<?php
/**
 * Created by PhpStorm.
 * User: Mortadhafff
 * Date: 02/04/2017
 * Time: 13:53
 */

namespace SocialPro\UserBundle\Controller;


use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class AjaxSearchController extends Controller
{
    public function updateDataAction(Request $request)
    {
        $data = $request->get('input');

        $em = $this->getDoctrine()->getEntityManager();
        $curr = $this->getUser()->getId();
        $query1 = $em->getRepository('SocialProUserBundle:User')->createQueryBuilder('u');
        $query=$query1->select('u.username')->where($query1->expr()->like('u.username',':data'))
            ->andWhere('u.id != :pa')
            ->setParameters(array('data'=> '%' . $data . '%','pa'=>$curr));
        $results = $query->getQuery()->getResult();

        $countryList = '<ul id="matchList">';
        $arr = array();
        foreach ($results as $result) {
            //$matchStringBold = preg_replace('/('.$data.')/i', '<strong>$1</strong>', $result['username']); // Replace text field input by bold one
            $countryList .= '<li class="lol" id="'.$result['username'].'">'.$result['username'].'</li>'; // Create the matching list - we put maching name in the ID too
            $arr[] = $result['username'];
        }
        $countryList .= '</ul>';

        $response = new JsonResponse();

        return  $response->setData(array('countryList' => $countryList,'resultat'=>$arr));
    }
    public function updateDataOwnerAction(Request $request)
    {
        $data = $request->get('input');

        $em = $this->getDoctrine()->getEntityManager();
        $curr = $this->getUser()->getId();
        $query1 = $em->getRepository('SocialProUserBundle:User')->createQueryBuilder('u');
        $query=$query1->select('u.username')->where($query1->expr()->like('u.username',':data'))
            ->andWhere('u.id != :pa')
            ->setParameters(array('data'=> '%' . $data . '%','pa'=>$curr));
        $results = $query->getQuery()->getResult();

        $countryList = '<ul id="matchList">';
        $arr = array();
        foreach ($results as $result) {
            //$matchStringBold = preg_replace('/('.$data.')/i', '<strong>$1</strong>', $result['username']); // Replace text field input by bold one
            $countryList .= '<li class="lol" id="'.$result['username'].'">'.$result['username'].'</li>'; // Create the matching list - we put maching name in the ID too
            $arr[] = $result['username'];
        }
        $countryList .= '</ul>';

        $response = new JsonResponse();

        return  $response->setData(array('countryList' => $countryList,'resultat'=>$arr));
    }
    public function sendNotifAction(Request $request)
    {
        $groupes = array();
        $em = $this->getDoctrine()->getManager();
        $curr = $this->getUser()->getId();
        $query = $em->createQuery(''
            . 'SELECT c.idGroupe,c.idUser '
            . 'FROM SocialProUserBundle:UserGroup c '
            . 'WHERE c.vu IS NULL AND c.idUser =:pa'
        )
            ->setParameters(array('pa'=>$curr));
        $results = $query->getResult();
        foreach ($results as $de){
            $groupes[]=$em->getRepository('SocialProUserBundle:Groupe')->findBy(array('idGroupe'=>$de['idGroupe']));
        }
        $nombrenotif = count($results);
        $countryList = '<ul class="menu">';
        $date3 = new \DateTime('now');

        foreach ($groupes as $result) {
                $interval =(new \DateTime())->diff($result[0]->getDateCreation());
            $countryList .= '<li><strong> Your are invited to join '.$result[0]->getNom().'</strong><small class="pull-right"><span class="livicon paddingright_10" data-n="timer" data-s="10"></span>il y a '.$result[0]->getDateCreation()->format('Y-m-d').'</small>
            <br><div data-id="'.$result[0]->getIdGroupe().'"><a id="accepter" align="left" width="30%" href="http://localhost/Social_Pro/web/app_dev.php/user/Affiche_Group/'.$result[0]->getIdGroupe().' ">Accepter&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a><a id="refuser" data-id="'.$result[0]->getIdGroupe().'" align="right" width="30%">Refuser <a/></div></li>';
        }
        $countryList .= '</ul>';
        $txtnotif = '<li class="dropdown-title">You have '.$nombrenotif.' notifications</li>';
        $response = new JsonResponse();
        $response->setData(array('countryList' => $countryList,'nombrenotif'=>$nombrenotif,'txtnotif'=>$txtnotif));
        return $response;
    }
    public function accepterNotifAction(Request $request)
    {
        $idGroupe = $request->get('input');
        var_dump($idGroupe);
        $em = $this->getDoctrine()->getManager();
        $curr = $this->getUser()->getId();
        $groupes=$em->getRepository('SocialProUserBundle:UserGroup')->findOneBy(array('idGroupe'=>$idGroupe,'idUser'=>$curr));
        $groupes->setVu("notif");
        $em->persist($groupes);
        $em->flush();
        $response = new JsonResponse();
        return $response;
    }
    public function refuserNotifAction(Request $request)
    {
        $idGroupe = $request->get('input');
        $em = $this->getDoctrine()->getManager();
        $curr = $this->getUser()->getId();
        $groupes=$em->getRepository('SocialProUserBundle:UserGroup')->findOneBy(array('idGroupe'=>$idGroupe,'idUser'=>$curr));
        $em->remove($groupes);
        $em->flush();
        $response = new JsonResponse();
        return $response;
    }
    /*public function sendUploadFileAction(Request $request,$id)
    {
        $groupes = array();
        $groupe_id = $request->get('input1');
        $em = $this->getDoctrine()->getManager();
        $curr = $this->getUser()->getId();

        $groupe=$em->getRepository('SocialProUserBundle:Groupe')->findOneBy(array('idGroupe'=>$id));
        $Messagegroupe=$em->getRepository('SocialProUserBundle:MessageGroupe')->findBy(array('idGroupe'=>$id));

        $countryList = '<div>';
        foreach ($Messagegroupe as $de){
            $countryList .= '<div class="conversation-start"><span>'.$de->getDateEnvoie()->format('Y-m-d').'</span></div><div class="bubble you">'.$de->getImageName().'</div>';
        }
        $countryList .= '</div>';
        $response = new JsonResponse();
        $response->setData(array('countryList' => $countryList));
        return $response;
    }*/
}
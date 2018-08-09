<?php
/**
 * Created by PhpStorm.
 * User: Mortadhafff
 * Date: 31/03/2017
 * Time: 18:44
 */

namespace SocialPro\UserBundle\Controller;


use FOS\UserBundle\Model\UserInterface;
use SocialPro\UserBundle\Entity\Groupe;
use SocialPro\UserBundle\Entity\MessageGroupe;
use SocialPro\UserBundle\Entity\User;
use SocialPro\UserBundle\Entity\UserGroup;
use SocialPro\UserBundle\Form\GroupeType;
use SocialPro\UserBundle\Form\MessageGroupeType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class GroupsController extends Controller
{
    public function groupsAction(Request $request)
    {
        $user = $this->getUser();
        if (!is_object($user) || !$user instanceof UserInterface) {
            return $this->redirectToRoute('fos_user_security_login');
        }
        $groupes = array();
        $em = $this->getDoctrine()->getManager();
        $groupe = new Groupe();
        $form = $this->createForm(GroupeType::class, $groupe);
        $form->handleRequest($request);
        $notif = "notif";
        $groupesuser=$em->getRepository('SocialProUserBundle:UserGroup')->findBy(array('idUser'=>$user,'vu'=>$notif));
        foreach ($groupesuser as $de){
            $groupes[]=$em->getRepository('SocialProUserBundle:Groupe')->findBy(array('idGroupe'=>$de->getIdGroupe()));
        }
        $groupesOwner=$em->getRepository('SocialProUserBundle:Groupe')->findBy(array('owner'=>$user));
        if($form->isValid())
        {
            $groupe->setOwner($user);
            $em->persist($groupe);
            $em->flush();
            $cont = $request->get('cont');
            for ($i=1;$i<=$cont;$i++){
                $u=$em->getRepository('SocialProUserBundle:User')->findOneBy(array('username'=>$request->get("".$i."")));
                $ug= new UserGroup();
                $ug->setIdUser($u->getId());
                $ug->setIdGroupe($groupe->getIdGroupe());
                $em->persist($ug);

                $em->flush();
                        $sujet=$request->get('invitation à un groupe');
                        $email=$u->getEmail();
                        $body="vous étes invité à regoindre le groupe".$groupe->getNom();
                        $body=($body);
                        $message= \Swift_Message::newInstance()

                            ->setSubject($sujet)
                            ->setFrom('mortadhafadhlaoui@gmail.com')
                            ->setTo($email)
                            ->setBody($body);
                        $this->get('mailer')->send($message);

            }
            foreach ($groupesuser as $de){
                $groupes[]=$em->getRepository('SocialProUserBundle:Groupe')->findBy(array('idGroupe'=>$de->getIdGroupe()));
            }
            $groupesOwner=$em->getRepository('SocialProUserBundle:Groupe')->findBy(array('owner'=>$user));
            return $this->redirectToRoute('groups',array('groupesOwner'=>$groupesOwner,'groupes'=>$groupes));
        }

        return $this->render("SocialProUserBundle::groups.html.twig", array(
            'groupes'=>$groupes,'groupesOwner'=>$groupesOwner,'user' => $user,'form'=>$form->createView()
        ));
    }
    public function AfficheGroupeAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $groupes=$em->getRepository('SocialProUserBundle:Groupe')->findAll();
        return $this->render('SocialProUserBundle::groups.html.twig',array('groupes'=>$groupes));
    }
    public function Affiche_GroupAction(Request $request,$id)
    {


        $user = $this->getUser();
        $em = $this->getDoctrine()->getManager();
        $groupe =  $em->getRepository('SocialProUserBundle:Groupe')->find(array('idGroupe'=>$id));
        $groupes=$em->getRepository('SocialProUserBundle:UserGroup')->findBy(array('idGroupe'=>$groupe->getIdGroupe(),'vu'=>"notif"));

        foreach ($groupes as $gr)
        {
            $users[]=$em->getRepository('SocialProUserBundle:User')->find(array('id'=>$gr->getIdUser()));

        }
        if (!empty($users))
        {
            foreach ($users as $uu)
            {
                if ($uu->getId() != $user->getId() and $groupe->getOwner()->getId() != $user->getId())
                {
                    $url = $this->generateUrl('social_pro_user_homepage');
                    $response = new RedirectResponse($url);
                    return $response;
                }
            }
        }

        $profiles = $em->getRepository('SocialProUserBundle:Profil')->findAll();
        $form = $this->createForm(GroupeType::class, $groupe);
        $messageGroupe = new MessageGroupe();
        $formMessage = $this->createForm(MessageGroupeType::class,$messageGroupe);
        $form->handleRequest($request);
        $formMessage->handleRequest($request);
        if($formMessage->isValid())
        {
            $messageGroupe->setIdGroupe($groupe);
            $messageGroupe->setImageSize(10);
            $messageGroupe->setIdEmetteur($this->getUser());
            $em->persist($messageGroupe);
            $em->flush();
            return $this->redirectToRoute('Affiche_Group',array("id"=>$groupe->getIdGroupe()));
        }
        $Messagegroupee=$em->getRepository('SocialProUserBundle:MessageGroupe')->findBy(array('idGroupe'=>$id));

        if($form->isValid())
        {
            $cont = $request->get('contgroupe');
            for ($i=1;$i<=$cont;$i++){
                $u=$em->getRepository('SocialProUserBundle:User')->findOneBy(array('username'=>$request->get("".$i."")));
                $ug= new UserGroup();
                $ug->setIdUser($u->getId());
                $ug->setIdGroupe($groupe->getIdGroupe());
                $em->persist($ug);

                $em->flush();
                $sujet=$request->get('invitation à un groupe');
                $email=$u->getEmail();
                $body="vous étes invité à regoindre le groupe".$groupe->getNom();
                $body=($body);
                $message= \Swift_Message::newInstance()

                    ->setSubject($sujet)
                    ->setFrom('mortadhafadhlaoui@gmail.com')
                    ->setTo($email)
                    ->setBody($body);
                $this->get('mailer')->send($message);

            }
            $em->persist($groupe);
            $em->flush();
            return $this->redirectToRoute('Affiche_Group',array("id"=>$groupe->getIdGroupe()));
        }

        if (!empty($users))
        {
            return $this->render('SocialProUserBundle::groupe.html.twig',array('user'=>$user,'users'=>$users,'Messagegroupee'=>$Messagegroupee,'groupe'=>$groupe,'profiles'=>$profiles,'form'=>$form->createView(),'formMessage'=>$formMessage->createView()));
        }else
            {
                return $this->render('SocialProUserBundle::groupe.html.twig',array('user'=>$user,'Messagegroupee'=>$Messagegroupee,'groupe'=>$groupe,'profiles'=>$profiles,'form'=>$form->createView(),'formMessage'=>$formMessage->createView()));
            }




    }
    public function Delete_GroupAction($id)
    {
        $user = $this->getUser();
        $em = $this->getDoctrine()->getManager();
        $groupesuser=$em->getRepository('SocialProUserBundle:UserGroup')->findBy(array('idGroupe'=>$id));
        foreach ($groupesuser as $de){
            $em->remove($de);
            $em->flush();
        }
        $groupe =  $em->getRepository('SocialProUserBundle:Groupe')->findOneBy(array('idGroupe'=>$id));
        $em->remove($groupe);
        $em->flush();
        return $this->redirectToRoute('groups');
    }

    public function downloadAction($filename)
    {

        $path = $this->get('kernel')->getRootDir(). "/../web/img/authors/";
        $content = file_get_contents($path.$filename);

        $response = new Response();

        //set headers
        $response->headers->set('Content-Type', 'mime/type');
        $response->headers->set('Content-Disposition', 'attachment;filename="'.$filename);

        $response->setContent($content);
        return $response;
    }
    public function searchgroupsAction(Request $request)
    {
        $data = $request->get('input');

        $em = $this->getDoctrine()->getEntityManager();
        $curr = $this->getUser()->getId();

        $results=$em->getRepository('SocialProUserBundle:UserGroup')->findBy(array('idUser'=>$curr,'vu'=>"notif"));
        foreach ($results as $kk)
        {
            $query1 = $em->getRepository('SocialProUserBundle:Groupe')->createQueryBuilder('u');
            $query = $query1->select('u')->where($query1->expr()->like('u.nom', ':data'))
                ->andWhere('u.idGroupe =:pa')
                ->setParameters(array('data' => '%' . $data . '%', 'pa' => $kk->getIdGroupe()));
            $groupss = $query->getQuery()->getResult();
        }

            $query1 = $em->getRepository('SocialProUserBundle:Groupe')->createQueryBuilder('u');
            $query = $query1->select('u')->where($query1->expr()->like('u.nom', ':data'))
                ->andWhere('u.owner =:pa')
                ->setParameters(array('data' => '%' . $data . '%', 'pa' => $curr));
            $resultss = $query->getQuery()->getResult();

        $vosgroupsList = '<div class="row" style="margin-top: 60px;" id="lolking">
                <div class="col-md-12">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <i class="livicon" data-name="rocket" data-size="16" data-loop="true" data-c="#fff" data-hc="white"></i>
                                <strong>Groupes que vous gérez</strong>
                            </h3>
                            <span class="pull-right clickable">
                                    <i class="glyphicon glyphicon-chevron-up"></i>
                                </span>
                        </div>
                        <div class="panel-body modal-panel">';
        $arr = array();
        foreach ($resultss as $result) {
            if ($result->getImageName() == null)
            {
                //$matchStringBold = preg_replace('/('.$data.')/i', '<strong>$1</strong>', $result['username']); // Replace text field input by bold one

                $matchStringBold = '<img data-toggle="magnify" class="media-object img-circle" src="http://localhost/Social_Pro/web/img/authors/la-parole-de-social-de-gens-de-rseau-de-medias-de-groupe-de-bulles-10346593.jpg"  alt=""/>';
            }
        else{
            $matchStringBold = '<img data-toggle="magnify" class="media-object img-circle" src="http://localhost/Social_Pro/web/img/authors/'.$result->getImageName().'"  alt=""/>';


        }

            $vosgroupsList .= '<div id="tab-activity" class="tab-pane fade in active">
                                <div class="tab-content">
                                    <div class="activity">
                                        <div class="imgs-profile">                                      
                                            <a class="pull-left" href="http://localhost/Social_Pro/web/app_dev.php/user/Affiche_Group/'.$result->getIdGroupe().'">                                              
                                            '.$matchStringBold.'
                                            </a>
                                            <div class="media-body">
                                                <strong><a href="http://localhost/Social_Pro/web/app_dev.php/user/Affiche_Group/'.$result->getIdGroupe().'">'.$result->getNom().'</a></strong>
                                                <br>
                                                <small class="text-muted">
                                                    created at '.$result->getDateCreation()->format('d/m/Y').'
                                                </small>
                                                <p>
                                                '.$result->getDescription().'                                                
                                                </p>
                                            </div>
                                        </div>
                                     </div>
                                </div>
                            </div>';
        }
        $vosgroupsList .= '</div>
                    </div>
                </div>
            </div>';
         $groupsgerer = '<div class="row" style="margin-top: 60px;" id="lolkinglolking">
                 <div class="col-md-12">
                     <div class="panel panel-primary">
                         <div class="panel-heading">
                             <h3 class="panel-title">
                                 <i class="livicon" data-name="piechart" data-size="16" data-loop="true" data-c="#fff" data-hc="white"></i>
                                 <strong>Vos groupes</strong>
                             </h3>
                             <span class="pull-right clickable">
                                     <i class="glyphicon glyphicon-chevron-up"></i>
                                 </span>
                         </div>
                         <div class="panel-body modal-panel">';

         foreach ($groupss as $result) {
             if ($result->getImageName() == null)
             {
                 $matchStringBold = '<img data-toggle="magnify" class="media-object img-circle" src="http://localhost/Social_Pro/web/img/authors/la-parole-de-social-de-gens-de-rseau-de-medias-de-groupe-de-bulles-10346593.jpg"  alt=""/>';
             }
             else{
                 $matchStringBold = '<img data-toggle="magnify" class="media-object img-circle" src="http://localhost/Social_Pro/web/img/authors/'.$result->getImageName().'"  alt=""/>';


             }
             $groupsgerer .= '<div id="tab-activity" class="tab-pane fade in active">
                                 <div class="tab-content">
                                     <div class="activity">
                                         <div class="imgs-profile">
                                             <a class="pull-left" href="http://localhost/Social_Pro/web/app_dev.php/user/Affiche_Group/'.$result->getIdGroupe().'">
                                             '.$matchStringBold.'
                                             </a>
                                             <div class="media-body">
                                                 <strong><a href="http://localhost/Social_Pro/web/app_dev.php/user/Affiche_Group/'.$result->getIdGroupe().'">'.$result->getNom().'</a></strong>
                                                 <br>
                                                 <small class="text-muted">
                                                     created at '.$result->getDateCreation()->format('d/m/Y').'
                                                 </small>
                                                 <p>
                                                 '.$result->getDescription().'
                                                 </p>
                                             </div>
                                         </div>
                                      </div>
                                 </div>
                             </div>';
         }
         $groupsgerer .= '</div>
                     </div>
                 </div>
             </div>';
        $response = new JsonResponse();

        return  $response->setData(array('vosgroupsList' => $vosgroupsList,'groupsgerer'=>$groupsgerer));
    }

}
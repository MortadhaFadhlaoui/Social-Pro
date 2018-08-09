<?php

namespace SocialPro\UserBundle\Controller;

use SocialPro\UserBundle\Entity\EvaluationEvent;
use SocialPro\UserBundle\Entity\Evenement;
use SocialPro\UserBundle\Entity\Invitation;
use SocialPro\UserBundle\Entity\ParticiperEvenement;
use SocialPro\UserBundle\Entity\User;
use SocialPro\UserBundle\Form\EvenementType;
use SocialPro\UserBundle\Form\InviterForm;
use SocialPro\UserBundle\SocialProUserBundle;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use SocialPro\UserBundle\Form\rechercheForm;


class EvenementController extends Controller
{
    public function ajoutAction(Request $request )
    {
        $evenement=new Evenement();
        $form= $this->createForm(EvenementType::class,$evenement);
        $msg="";

        if ($request->isMethod('POST')) {
            $form->submit($request->request->get($form->getName()));

            if ($form->isSubmitted() && $form->isValid()) {
                $evenement->setIdOrganisateur($this->getUser());
                $em = $this->getDoctrine()->getManager();
                $ev = $evenement;
                $em->persist($evenement);
                $em->flush();

                $conf = $evenement->getConfidentialite();
                $nomev=$evenement->getNom();

                $organisateur=$evenement->getIdOrganisateur();
                   $nomorg= $em->getRepository('SocialProUserBundle:User')->find($organisateur);
                $dateev=$evenement->getDate()->format('Y-m-d ');

                if ($conf === "Public") {
                    $em = $this->getDoctrine()->getManager();
                    $invite = $em->getRepository('SocialProUserBundle:User')->findAll();
                    foreach ($invite as $value) {
                        $invitaion = new Invitation();
                        $invitaion->setIdEmetteur($this->getUser());
                        $invitaion->setIdRecepteur($value);
                        $invitaion->setIdEvenement($evenement);
                        $nomev=$evenement->getNom();
                        $datev=$evenement->getDate()->format('Y-m-d H:i:s');
                        $lieuev=$evenement->getLieu();
                        $invitaion->setContenu("Vous etes invité à l'evenement public $nomev qui aura lieu le $datev dans $lieuev votre presence est souhaitée");
                        $invitaion->setEtat(0);
                        $em->persist($invitaion);
                        $em->flush();
                    }

                } else {

                    return $this->redirect($this->generateUrl('ajoutInvit', array('id' => $ev->getIdEvenement())), 301);

                }
                $em=$this->getDoctrine()->getManager();
                $check = $em->getRepository("SocialProUserBundle:ParticiperEvenement")->findBy(array('idEvenement' => $evenement->getIdEvenement()));
                var_dump($check);
                $n=count($check);
                return $this->redirect($this->generateUrl('list_event', array('n' => $n)), 301);
               // return ($this->redirectToRoute("list_event"));
            }
            $msg="date invalide ! ";

        }

        return $this->render('SocialProUserBundle:evenement:addEvent.html.twig',array('form'=>$form->createView(),'msg'=>$msg));
        var_dump($msg);
    }
    public function affichageAction(Request $request)
    {
        $evenement = new Evenement();
        $id =$this->getUser();
        $em=$this->getDoctrine()->getManager();
        $n=0;
        $Form=$this->createForm(rechercheForm::class,$evenement);

        $Form->handleRequest($request);
        if ($Form->isValid())
        {
            $evenement=$em->getRepository("SocialProUserBundle:Evenement")->findBy(array('nom'=>$evenement->getNom()));

        }
        else
        {
            $evenement=$em->getRepository("SocialProUserBundle:Evenement")->findEventDQL($id);



        }


        return $this ->render('SocialProUserBundle:evenement:Liste.html.twig',array("form"=>$Form->createView(),'evenements'=>$evenement,'n'=>$n));

    }
    public function rechercheAction(Request $request)
    {

        $evenement= new Evenement();
        $em=$this->getDoctrine()->getManager();
        $Form=$this->createForm(rechercheForm::class,$evenement);

        $Form->handleRequest($request);
        if ($Form->isValid())
        {
            $evenement=$em->getRepository("SocialProUserBundle:Evenement")->findBy(array('nom'=>$evenement->getNom()));

        }
        else
        {
            $evenement=$em->getRepository("SocialProUserBundle:Evenement")->findAll();
        }

        return $this ->render('SocialProUserBundle:evenement:Liste.html.twig',array("form"=>$Form->createView(),'evenements'=>$evenement));

    } public function deleteAction($id)
{
    $em=$this->getDoctrine()->getManager();
    $evenement=$em->getRepository('SocialProUserBundle:Evenement')->find($id);
    $ide=$evenement;
    $em->getRepository("SocialProUserBundle:Invitation")->deleteInvitDQL($ide);
    $em->getRepository("SocialProUserBundle:ParticiperEvenement")->deleteParticipDQL($ide);
    $em->getRepository("SocialProUserBundle:EvaluationEvent")->deleteRatingDQL($ide);
    $em->flush();

    $em->remove($evenement);
    $em->flush();



    return ($this->redirectToRoute("list_event"));
}
    public function updateAction(Request $request,$id)
    {
        /*$em=$this->getDoctrine()->getManager();
        $evenement=$em->getRepository('SocialProUserBundle:Evenement')->find($id);
        $form= $this->createForm(EvenementType::class,$evenement);
        $form->handleRequest($request);
        if($form->isValid())
        {
            $em->persist($evenement);
            $em->flush();
            return $this->redirect($this->generateUrl('list_event'));
        }
        return $this->render('SocialProUserBundle:evenement:addEvent.html.twig',array('form'=>$form->createView()));
        */
        $em=$this->getDoctrine()->getManager();
        $evenement=$em->getRepository('SocialProUserBundle:Evenement')->find($id);
        $form= $this->createForm(EvenementType::class,$evenement);
        $msg="";

        if ($request->isMethod('POST')) {
            $form->submit($request->request->get($form->getName()));

            if ($form->isSubmitted() && $form->isValid()) {

                $em->persist($evenement);
                $em->flush();
                return $this->redirect($this->generateUrl('list_event'));
            }
            $msg="date invalide ! ";
        }

        return $this->render('SocialProUserBundle:evenement:addEvent.html.twig',array('form'=>$form->createView(),'msg'=>$msg));
        var_dump($msg);

    }
    public function findInvitAction(){
        $id =$this->getUser();
        $em=$this->getDoctrine()->getManager();
        $invitation=$em->getRepository("SocialProUserBundle:Invitation")->findInvitDQL($id);
        return $this ->render('SocialProUserBundle:evenement:ListeInvit.html.twig',array('invitations'=>$invitation));
    }
    public function acceptInvitAction($id){
        $em=$this->getDoctrine()->getManager();
        $invitation=$em->getRepository('SocialProUserBundle:Invitation')->find($id);
        $invitation->setEtat(1);
        $em->flush();
        $ide=$invitation->getIdEvenement();
       $idp=$this->getUser();

       $particip = new ParticiperEvenement();
       $particip->setIdEvenement($ide);
       $particip->setIdUser($idp);
       $particip->setEtat(0);
       $em->persist($particip);
       $em->flush();


        return ($this->redirectToRoute("findInvit"));
    }
    public function refusetInvitAction($id){
        $em=$this->getDoctrine()->getManager();
        $invitation=$em->getRepository('SocialProUserBundle:Invitation')->find($id);
        $invitation->setEtat(2);
        $em->flush();

        return ($this->redirectToRoute("findInvit"));
    }
    public function ajoutInvitAction(Request $request , $id )
    {
        $msg="";
        $em=$this->getDoctrine()->getManager();
        $invitaion=new Invitation();
        $form= $this->createForm(InviterForm::class,$invitaion);
        $form->handleRequest($request);


        $ev=$em->getRepository('SocialProUserBundle:Evenement')->find($id);
        $nomev=$ev->getNom();
        $datev=$ev->getDate()->format('Y-m-d ');
        $lieuev=$ev->getLieu();

        if($form->isValid())
        {



            $em=$this->getDoctrine()->getManager();
            $invitaion->setEtat(0);
            $invitaion->setIdEvenement($ev);
            $invitaion->setIdEmetteur($this->getUser());
            $invitaion->setContenu("Vous etes invité à l'evenement public $nomev qui aura lieu le $datev dans $lieuev votre presence est souhaitée");
            $em->persist($invitaion);
            $em->flush();
            $idr= $invitaion->getIdRecepteur();
            $id=$invitaion->getIdEvenement();
            $idiv=$invitaion->getIdInvitation();

            $check = $em->getRepository("SocialProUserBundle:Invitation")->findBy(array('idRecepteur' => $idr, 'idEvenement' => $id));
            $n= count($check);
           // $check=$em->getRepository("SocialProUserBundle:Invitation")->checkInvitDQL($id,$idr);
            //var_dump($check);
            //var_dump($n);
            if($n>1)
            {
                $msg="vous avez deja inviter cette personne";
               $invitationdelete=$em->getRepository('SocialProUserBundle:Invitation')->find($idiv);
                $em->remove($invitationdelete);
                $em->flush();//pour executer la equette de supression



            }
            else
            {
                $em->flush();
            }
          //  $em->flush();

           // return $this->redirect($this->generateUrl('findInvit'));
        }
        return $this->render('SocialProUserBundle:evenement:addInvit.html.twig',array('form'=>$form->createView(),'msg'=>$msg));

    }
    public function validerInvitAction(){
        return ($this->redirectToRoute("list_event"));
    }
    public function checkAction($id){
        $em=$this->getDoctrine()->getManager();
        $check = $em->getRepository("SocialProUserBundle:ParticiperEvenement")->findBy(array('idEvenement' => $id));
        $n=count($check);
        return $this ->render('SocialProUserBundle:evenement:Liste.html.twig',array('n'=>$n));
      // return $this->redirect($this->generateUrl('list_event', array('id' => $id)), 301);
       // return $this ->render('SocialProUserBundle:evenement:Liste.html.twig',array('evenements'=>$evenement,'n'=>$n));


        // $em=$this->getDoctrine()->getManager();
        //$check = $em->getRepository("SocialProUserBundle:ParticiperEvenement")->findBy(array('idEvenement' => $id));
        //$n=count($check);
        //return $this ->render('SocialProUserBundle:evenement:Liste.html.twig',array('n'=>$n));

    }
    public function affichageParticipAction(Request $request){
        $id =$this->getUser();
        $em=$this->getDoctrine()->getManager();
        $participation = $em->getRepository("SocialProUserBundle:ParticiperEvenement")->findBy(array('idUser' => $id));
        $notee=new EvaluationEvent();
        $form= $this->createForm(\SocialPro\UserBundle\Form\ParticiperEvenement::class,$notee);
        $form->handleRequest($request);
        if($form->isValid())
        {

            $notee->setIdUser($this->getUser());
            $em->persist($notee);
            $em->flush();

           // return $this->redirect($this->generateUrl('list_event_particip'));
        }
        return $this ->render('SocialProUserBundle:evenement:ListeParticipe.html.twig',array('participations'=>$participation,'form'=>$form->createView()));
    }
    public function ajoutPAction(Request $request,$id)
    {
        $msg="";
        $em=$this->getDoctrine()->getManager();

        $evenement=$em->getRepository('SocialProUserBundle:Evenement')->find($id);
        $notee=new EvaluationEvent();
        $idu=$this->getUser();
        $ne = $em->getRepository("SocialProUserBundle:EvaluationEvent")->findBy(array('idEvent' =>$id));
        $nbp= $em->getRepository("SocialProUserBundle:ParticiperEvenement")->findBy(array('idEvenement' =>$id));
        $evaluation = $em->getRepository("SocialProUserBundle:EvaluationEvent")->findBy(array('idEvent' => $id,'idUser'=>$idu));
        $rating = $em->getRepository("SocialProUserBundle:EvaluationEvent")->findOneBy(array('idEvent' => $id,'idUser'=>$idu));
        $nev=count($evaluation);
      //  $a= count($rating);
       // $rate=0.0;
        if($rating!=null){
            $rate=$rating->getRating();
        }
        else
        {
            $rate=0;
        }

        $n=count($ne);
        $nb=count($nbp);
        //var_dump($nb);
        //var_dump($n);
        $form= $this->createForm(\SocialPro\UserBundle\Form\ParticiperEvenement::class,$notee);
        $form->handleRequest($request);
        if($form->isValid())
        {
            $notee->setIdUser($this->getUser());
            $notee->setIdEvent($evenement);
            $em->persist($notee);
            $em->flush();
             return $this->redirect($this->generateUrl('list_event_particip'));
        }

        return $this->render('SocialProUserBundle:evenement:rating.html.twig',array('form'=>$form->createView(),'evenement'=>$evenement,'nn'=>$n,'nb'=>$nb,'nevv'=>$nev,'evall'=>$rate));

    }
    }


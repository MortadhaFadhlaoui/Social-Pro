<?php

namespace SocialPro\AdminBundle\Controller;

use SocialPro\UserBundle\Entity\CommentaireProposition;
use SocialPro\UserBundle\Entity\Proposition;
use SocialPro\UserBundle\Entity\PropositionVote;
use SocialPro\UserBundle\Entity\Profil;
use SocialPro\UserBundle\Entity\User;
use SocialPro\UserBundle\Entity\Equipe;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;

class PropositionController extends Controller
{
    public function listAction(Request $request)
    {
        $user = $this->getUser();
        $vp = array();
        $vc = array();
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $comp=$em->getRepository('SocialProUserBundle:Proposition')->findAll();
            foreach($comp as $p)
            {   $req=$em->getRepository('SocialProUserBundle:PropositionVote')->findBy(array('avis'=>'pour','idProposition'=>$p->getIdProposition()));
                $req2=$em->getRepository('SocialProUserBundle:PropositionVote')->findBy(array('avis'=>'contre','idProposition'=>$p->getIdProposition()));
                if($req == null)
                {
                    $vp[]=0;
                }
                else
                {
                    $vp[]=$req;
                }
                if($req2 == null)
                {
                    $vc[]=0;
                }
                else
                {
                    $vc[]=$req2;
                }

            }





            return $this->render("AdminBundle:Proposition:proposition.html.twig",array('props'=>$comp,'vp'=>$vp,'vc'=>$vc,'user'=>$user));
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
    public function pourAction(Request $request,$id)
    {
        $user = $this->getUser();
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $vote= new PropositionVote();
            $vote->setIdProposition($id);
            $vote->setIdMembre($user->getId());
            $vote->setAvis('pour');

            $em->persist($vote);
            $em->flush();
            //var_dump($e[0]->getIdUser());
            return new RedirectResponse($router->generate('proposition'), 307);
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }

    public function contreAction(Request $request,$id)
    {
        $user = $this->getUser();
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $vote= new PropositionVote();
            $vote->setIdProposition($id);
            $vote->setIdMembre($user->getId());
            $vote->setAvis('contre');

            $em->persist($vote);
            $em->flush();
            //var_dump($e[0]->getIdUser());
            return new RedirectResponse($router->generate('proposition'), 307);
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
    public function addPropositionAction(Request $request)
    {
        $prop=new Proposition();
        $user = $this->getUser();
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            if($request->isMethod('POST'))
            {
                $titre=$request->get('titre');
                $sujet=$request->get('sujet');
                $prop->setTitre($titre);
                $prop->setSujet($sujet);
                $prop->setIdMembreProposition($user);
            }


            $em->persist($prop);
            $em->flush();
            //var_dump($e[0]->getIdUser());
            return new RedirectResponse($router->generate('proposition'), 307);
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
    public function pdfAction()
    {
        $user = $this->getUser();
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $comp=$em->getRepository('SocialProUserBundle:Proposition')->findAll();
            foreach($comp as $p)
            {   $req=$em->getRepository('SocialProUserBundle:PropositionVote')->findBy(array('avis'=>'pour','idProposition'=>$p->getIdProposition()));
                $req2=$em->getRepository('SocialProUserBundle:PropositionVote')->findBy(array('avis'=>'contre','idProposition'=>$p->getIdProposition()));
                if($req == null)
                {
                    $vp[]=0;
                }
                else
                {
                    $vp[]=$req;
                }
                if($req2 == null)
                {
                    $vc[]=0;
                }
                else
                {
                    $vc[]=$req2;
                }

            }
            $html = $this->renderView("AdminBundle:Proposition:proposition.html.twig",array('props'=>$comp,'vp'=>$vp,'vc'=>$vc,'user'=>$user));
            $html2pdf = new \Html2Pdf_Html2Pdf('P','A4','fr');
            $html2pdf->pdf->SetDisplayMode('real');
            $html2pdf->writeHTML($html);
            $html2pdf->Output('Facture.pdf');


            return new Response();
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function DeletePropositionAction($id)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em=$this->getDoctrine()->getManager();
            $prop=$em->getRepository('SocialProUserBundle:Proposition')->find($id);




            $em->remove($prop);
            $em->flush();

            return new RedirectResponse($router->generate('proposition'), 307);
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function CommentPropositionAction($id)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $propcom=$em->getRepository('SocialProUserBundle:Proposition')->find($id);

            $propuser=$em->getRepository('SocialProUserBundle:User')->findOneBy(array('id'=>$propcom->getIdMembreProposition()->getId()));

            $comprop = $em->getRepository('SocialProUserBundle:CommentaireProposition')->findby(array('idProposition'=>$id));
            if ($comprop == null)
            {
                return $this->render("AdminBundle:Proposition:comment.html.twig",array('propcom'=>$propcom,'propuser'=>$propuser,'comprop'=>$comprop));
            }
            else
            {
                foreach($comprop as $comu)
                {

                    $profil=$em->getRepository('SocialProUserBundle:Profil')->findBy(array('iduser'=>$comu->getIdUser()));
                    $comu->getIdUser()->setIdProfil($profil);
                }

                $profil=$comprop[0]->getIdUser()->getIdProfil();
                return $this->render("AdminBundle:Proposition:comment.html.twig",array('propcom'=>$propcom,'propuser'=>$propuser,'comprop'=>$comprop));
            }
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function AddCommentAction(Request $request)
    {
        $id=$request->get('id');
        var_dump($id);
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $prop=new CommentaireProposition();
            $user = $this->getUser();
                $em = $this->getDoctrine()->getManager();
                if($request->isMethod('POST'))
                {
                    $titre=$request->get('text');
                    $prop->setText($titre);
                    $prop->setIdUser($user);
                    $propcom=$em->getRepository('SocialProUserBundle:Proposition')->find($id);
                    $prop->setIdProposition($propcom);
                }
                $em->persist($prop);
                $em->flush();
            return new RedirectResponse($router->generate('CommentProposition',array('id'=>$id)));
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }


}
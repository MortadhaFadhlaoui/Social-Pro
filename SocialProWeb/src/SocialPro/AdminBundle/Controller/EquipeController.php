<?php

namespace SocialPro\AdminBundle\Controller;

use SocialPro\UserBundle\Entity\User;
use SocialPro\UserBundle\Entity\Equipe;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class EquipeController extends Controller
{

    public function listAction(Request $request,$id)
    {
        $e=new Equipe();


        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $e = $em->getRepository('SocialProUserBundle:Equipe')->findBy(array('idProjet'=>$id));
            $libreUser=$em->getRepository('SocialProUserBundle:User')->findBy(array('statut'=>'libre','role'=>'user'));
            $comp=$em->getRepository('SocialProUserBundle:Competence')->findAll();


            //var_dump($e[0]->getIdUser());
            return $this->render("AdminBundle:Equipe:equipe.html.twig",array('userss'=>$e[0]->getIdUser(),'luser'=>$libreUser,'comp'=>$comp,'id'=>$id,'equipe'=>$e[0]));
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function affecteAction(Request $request,$eq)
    {
        $e = new Equipe();
        $u = new User();
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            if($request->isMethod('POST'))
            {
                if ($rech=$request->get('s1')!=null)
                {
                    $rech=$request->get('s1');
                    $e = $em->getRepository('SocialProUserBundle:Equipe')->findBy(array('idEquipe'=>$eq));
                    $u = $em->getRepository('SocialProUserBundle:User')->findBy(array('id'=>$rech));


                    $e[0]->addUser($u[0]);
                    $em->persist($e[0]);
                    $em->flush();

                    $u[0]->addEquipe($e[0]);


                    $u[0]->setStatut('occupe');
                    $em->persist($u[0]);
                    $em->flush();
                    return new RedirectResponse($router->generate('equipe',array('id'=>1)), 307);

                }
            }

        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
    public function DeleteMembreAction($id,$eq)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em=$this->getDoctrine()->getManager();
            $user=$em->getRepository('SocialProUserBundle:User')->find($id);
            $equipe=$em->getRepository('SocialProUserBundle:Equipe')->find($eq);



            $user->removeEquipe($equipe);
            $equipe->removeUser($user);
            $em->flush();

            return new RedirectResponse($router->generate('equipe',array('id'=>1)), 307);
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function skillAction(Request $request)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {


            $id = $request->request->get('skill');
           $id2= $request->query->get('skill');
            if($request->isMethod('POST'))
            {
                var_dump($id);
            }
            var_dump($id);
            var_dump($id2);

            var_dump($request);



           // var_dump($skill);
            $em = $this->getDoctrine()->getManager();
            $u = $em->getRepository('SocialProUserBundle:User')->findBy(array('idCompetence'=>$id));
            return new Response($u);
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
}
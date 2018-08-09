<?php

namespace SocialPro\AdminBundle\Controller;

use belousovr\belousovChatBundle\Entity\Messages;
use SocialPro\UserBundle\Entity\Profil;
use SocialPro\UserBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;

class DefaultController extends Controller
{
    public function indexAction()
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            return $this->render('AdminBundle::admin.html.twig');
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
    public function usersAction(Request $request)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $userss = $em->getRepository('SocialProUserBundle:User')->findAll();
            if($request->isMethod('POST'))
            {
                if ($rech=$request->get('rech')!=null)
                {
                    $rech=$request->get('rech');
                    $userss=$em->getRepository('SocialProUserBundle:User')->findBy(array('nom'=>$rech));
                    return $this->render('AdminBundle:Default:users.html.twig',array('userss'=>$userss));
                }
            }
            //var_dump($users);
            return $this->render("AdminBundle:Default:users.html.twig",array('userss'=>$userss));
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function DeleteUserAction($id)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em=$this->getDoctrine()->getManager();
            $user=$em->getRepository('SocialProUserBundle:User')->find($id);
            $profils=$em->getRepository('SocialProUserBundle:Profil')->findProfileByUser($id);
            $em->getRepository(Messages::class)->DeletMessagebyUser($id);
            if (!empty($profils))
            {
             foreach ($profils as $profil)
             {
                 $em->remove($profil);
             }
            }
            $em->remove($user);
            $em->flush();
            return $this->redirectToRoute('users');
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }

    public function EventsAction(Request $request)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');
        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $Events = $em->getRepository('SocialProUserBundle:Evenement')->findAll();


            if($request->isMethod('POST'))
            {
                if ($rech=$request->get('rech')!=null)
                {
                    $rech=$request->get('rech');
                    $Events=$em->getRepository('SocialProUserBundle:Evenement')->findBy(array('nom'=>$rech));
                    return $this->render('AdminBundle:Default:Events.html.twig',array('Eventss'=>$Events));
                }
            }
            //var_dump($users);
            return $this->render("AdminBundle:Default:Events.html.twig",array('Eventss'=>$Events));
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function findEventAction($id)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em=$this->getDoctrine()->getManager();
            $event=$em->getRepository('SocialProUserBundle:Evenement')->find($id);
            $s=0;
            $nbp= $em->getRepository("SocialProUserBundle:ParticiperEvenement")->findBy(array('idEvenement' =>$id));
            $n=count($nbp);
            $rate=$em->getRepository("SocialProUserBundle:EvaluationEvent")->findBy(array('idEvent' =>$id));
            foreach ($rate as $value)
            {
                $s=$s+$value->getRating();
            }
            $nb=count($rate);
            if ($nb==0)
            {
                $moy=0;
            }
            else{
                $moy=($s/$nb);
            }

            return $this->render('AdminBundle:Default:Events1.html.twig', array('events' => $event,'nbp'=>$n,'moyenne'=>$moy));
            // return $this->redirect($this->generateUrl('showEvent', array('events' => $event)), 301);

        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

    }
    public function chartPieAction()
    {


        $em = $this->getDoctrine()->getManager();

        $typl = 0;
        $typp = 0;


        $event = $em->getRepository('SocialProUserBundle:Evenement') ->findAll();
        foreach ($event as $e) {
            if ($e->getType()=="profesionelle" )

            {
                $typp++;

            }
            else if ($e->getType()=="loisir")

            {
                $typl++;
            }


        }
        $ob = new Highchart();
        $ob->chart->renderTo('piechart');
        $ob->title->text('les differentes types des evenements' );
        $ob->plotOptions->pie(array(
            'allowPointSelect' => false,
            'cursor' => 'pointer',
            'dataLabels' => array('enabled' => false),
            'showInLegend' => true
        ));
        $data = array(
            array('profesionelle', $typp),
            array('loisir', $typl),


        );
        $ob->series(array(array('type' => 'pie', 'name' =>
            'Nombre evenements', 'data' => $data)));

        return $this->render('AdminBundle:Default:pie.html.twig', array(
            'chart' => $ob,
        ));

    }
}

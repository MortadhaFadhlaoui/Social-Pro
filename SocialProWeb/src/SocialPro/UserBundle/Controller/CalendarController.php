<?php

namespace SocialPro\UserBundle\Controller;


use SocialPro\UserBundle\Entity\Profil;
use SocialPro\UserBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;

class CalendarController extends Controller
{
    public function ListAction(Request $request)
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {
            $em = $this->getDoctrine()->getManager();
            $comp=$em->getRepository('SocialProUserBundle:Evenement')->findAll();

    //calendar
            /*
            $googleCalendar = $this->get('fungio.google_calendar');
            $googleCalendar->setRedirectUri('http://' . $_SERVER['HTTP_HOST'] . '/Social_Pro/oauth2callback.php');

            if ($request->query->has('code') && $request->get('code')) {
                $client = $googleCalendar->getClient($request->get('code'));
            } else {
                $client = $googleCalendar->getClient();
            }

           if (is_string($client)) {
                return new RedirectResponse($client);
            }

            $events = $googleCalendar->getEventsForDate('achref adala', new \DateTime('now'));

            //*/

            return $this->render('SocialProUserBundle:Calendar:calendar.html.twig',array('events'=>$comp));
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }

}
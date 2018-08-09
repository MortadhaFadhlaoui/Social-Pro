<?php

namespace SocialPro\UserBundle\Controller;

use belousovr\belousovChatBundle\Form\ChatType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;

class DefaultController extends Controller
{
    public function indexAction()
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_USER') || $authChecker->isGranted('ROLE_ADMIN')) {

            return $this->render('SocialProUserBundle::master.html.twig');
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
    public function chatrendringAction()
    {
        $em = $this->getDoctrine()->getManager();
        $currentUser =$this->getUser();
        $profile = $em->getRepository('SocialProUserBundle:Profil')->findProfileByUser($currentUser->getId());
        $users = $em->getRepository('SocialProUserBundle:User')->findAll();

        $actionUrl = $this->generateUrl(
            'belousov_chat_ajax_send_message'
        );

        $getMessageUrl = $this->generateUrl(
            'belousov_chat_ajax_get_message'
        );
        $vuMessageUrl = $this->generateUrl(
            'belousov_chat_ajax_vu_message'
        );

        $profiles = $em->getRepository('SocialProUserBundle:Profil')->FindAllProfile();
        $chatForm = $this->createForm(ChatType::class, null, array('action' => $actionUrl));

        // var_dump($profiles);
        if (!empty($profile[0]))
        {
            return $this->render('SocialProUserBundle::chatrendring.html.twig', array(
                'chatForm' => $chatForm->createView(), 'users' => $users, 'getMessageUrl' => $getMessageUrl,'vuMessageUrl' => $vuMessageUrl, 'currentUser' => $currentUser,'profile' => $profile[0],
                'profiles' =>$profiles
            ));
        }
        else
        {
            return $this->render('SocialProUserBundle::chatrendring.html.twig', array(
                'chatForm' => $chatForm->createView(), 'users' => $users, 'getMessageUrl' => $getMessageUrl,'vuMessageUrl' => $vuMessageUrl, 'currentUser' => $currentUser,
                'profiles' =>$profiles
            ));
        }
    }
    public function homeAction()
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_USER') || $authChecker->isGranted('ROLE_ADMIN')) {
            return $this->render('SocialProUserBundle:Default:home.html.twig');
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
    public function ReadingAction()
    {
        $NonvuMessageUrl = $this->generateUrl(
            'belousov_chat_ajax_Nonvu_message'
        );
        return $this->render('SocialProUserBundle::Reading.html.twig',array('NonvuMessageUrl' => $NonvuMessageUrl,));
    }
    /**
     * @Template()
     */
    public function whoIsOnlineAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $users = $em->getRepository('SocialProUserBundle:User')->getActive();
        //var_dump($users);
        $currentUser = $this->getUser();
        $usersList = '<div id="user-profiles-list-basic"><div id="friends">';
        foreach ($users as $user)
        {
            if ($user->getId() !=$currentUser->getId())
            {
                $profile = $em->getRepository('SocialProUserBundle:Profil')->findOneBy(array('iduser'=>$user->getId()));
                if (!empty($profile) and !empty($profile->getImageName()))
                {
                    $usersList .= '<div id="friendsideshow" onclick="return false;" data-number="'.$user->getId().'" class="friend"><img src="/Social_Pro/web/img/authors/'.$profile->getImageName().'" /><strong>'.$user->getUsername().'</strong><br><div class="status available"></div></div>';
                }
                else
                {
                    $usersList .= '<div id="friendsideshow" onclick="return false;" data-number="'.$user->getId().'" class="friend"><img src="/Social_Pro/web/img/authors/user-avatar-placeholder.png" /><strong>'.$user->getUsername().'</strong><br><div class="status available"></div></div>';
                }
            }
        }
        $usersList .= '</div></div>';
        $response = new JsonResponse();

        return  $response->setData(array('userslist' => $usersList));
        //return $this->render('SocialProUserBundle::online.html.twig',array('users' => $users));
    }
}

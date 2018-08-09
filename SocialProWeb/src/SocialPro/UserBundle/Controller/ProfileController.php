<?php
/**
 * Created by PhpStorm.
 * User: Mortadhafff
 * Date: 21/03/2017
 * Time: 14:42
 */

namespace SocialPro\UserBundle\Controller;

use belousovr\belousovChatBundle\Form\ChatType;
use FOS\UserBundle\Event\FilterUserResponseEvent;
use FOS\UserBundle\Event\FormEvent;
use FOS\UserBundle\Event\GetResponseUserEvent;
use FOS\UserBundle\Form\Factory\FactoryInterface;
use FOS\UserBundle\FOSUserEvents;
use FOS\UserBundle\Model\UserInterface;
use FOS\UserBundle\Model\UserManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Security\Core\Exception\AccessDeniedException;
use FOS\UserBundle\Controller\ProfileController as BaseController;
use SocialPro\UserBundle\Entity\Profil;
use SocialPro\UserBundle\Form\ProfilType;

class ProfileController extends Controller
{
    public function profileAction(Request $request)
    {
        $user = $this->getUser();
        if (!is_object($user) || !$user instanceof UserInterface) {
            return $this->redirectToRoute('fos_user_security_login');
        }
        $date= new \DateTime('now');
        $em = $this->getDoctrine()->getManager();
        $profile = $em->getRepository('SocialProUserBundle:Profil')->findProfileByUser($user->getId());
        if (!empty($profile))
        {
            $profil = $profile[0];
            $form = $this->createForm(ProfilType::class, $profil);
            $form->handleRequest($request);
            if($form->isValid())
            {
                $profil->setIduser($user);
                $em->persist($profil);
                $em->flush();
                return $this->redirectToRoute('profile_show');
            }
            return $this->render('SocialProUserBundle::profile.html.twig', array(
                'user' => $user,'profile'=>$profil,'form'=>$form->createView()
            ,'date'=>$date));
        }else
        {
            $profil = new Profil();
            $form = $this->createForm(ProfilType::class, $profil);


            $form->handleRequest($request);


            if($form->isValid())
            {
                $profil->setIduser($user);
                $em->persist($profil);
                $em->flush();
                return $this->redirectToRoute('profile_show');
            }
            return $this->render('SocialProUserBundle::profile.html.twig', array(
                'user' => $user,'profile'=>$profil,'form'=>$form->createView()
            ,'date'=>$date));
        }
    }
    public function editAction(Request $request)
    {
        $router = $this->container->get('router');
        $user = $this->getUser();
        if (!is_object($user) || !$user instanceof UserInterface) {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }

        /** @var $dispatcher EventDispatcherInterface */
        $dispatcher = $this->get('event_dispatcher');

        $event = new GetResponseUserEvent($user, $request);
        $dispatcher->dispatch(FOSUserEvents::PROFILE_EDIT_INITIALIZE, $event);

        if (null !== $event->getResponse()) {
            return $event->getResponse();
        }

        /** @var $formFactory FactoryInterface */
        $formFactory = $this->get('fos_user.profile.form.factory');

        $form = $formFactory->createForm();
        $form->setData($user);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var $userManager UserManagerInterface */
            $userManager = $this->get('fos_user.user_manager');

            $event = new FormEvent($form, $request);
            $dispatcher->dispatch(FOSUserEvents::PROFILE_EDIT_SUCCESS, $event);

            $userManager->updateUser($user);

            if (null === $response = $event->getResponse()) {
                $url = $this->generateUrl('profile_show');
                $response = new RedirectResponse($url);
            }

            $dispatcher->dispatch(FOSUserEvents::PROFILE_EDIT_COMPLETED, new FilterUserResponseEvent($user, $request, $response));

            return $response;
        }

        return $this->render('FOSUserBundle:ChangePassword:change_password.html.twig', array(
            'f' => $form->createView(),
        ));
    }

}
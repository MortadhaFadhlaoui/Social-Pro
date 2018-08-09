<?php
/**
 * Created by PhpStorm.
 * User: bechir23
 * Date: 12/04/2017
 * Time: 00:43
 */

namespace SocialPro\UserBundle\Controller;

use SocialPro\UserBundle\Entity\Commentaire;
use SocialPro\UserBundle\Entity\Publication;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class PublicationController extends Controller
{
    /**
     * Lists all publication entities.
     *
     */
    public function indexAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $publication = new Publication();
        $commentaire = new Commentaire();

        $form = $this->createForm('SocialPro\UserBundle\Form\PublicationType', $publication);
        $form->handleRequest($request);
        $form1 = $this->createForm('SocialPro\UserBundle\Form\Commentaire', $commentaire);
        $form1->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($publication);
            $em->flush();

            return $this->redirectToRoute('publication_show', array('idPublication' => $publication->getIdpublication()));
        }

        if ($form1->isSubmitted() && $form1->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($commentaire);
            $em->flush();

            return $this->redirectToRoute('publication_show', array('idCommentaire' => $commentaire->getIdCommentaire()));
        }

        $publications = $em->getRepository('SocialProUserBundle:Publication')->findAll();

        $profiles = $em->getRepository('SocialProUserBundle:Profil')->findAll();
        return $this->render('SocialProUserBundle:publication:index.html.twig', array(
            'profiles'=>$profiles,
            'publications' => $publications,'publication' => $publication,
            'form' => $form->createView(),'publication' => $publication,
            'form_insert' => $form->createView(),
            'form1' => $form1->createView(),
        ));
    }
    public function new2Action(Request $request,$idPublication)
    {


        $commentaire = new Commentaire();
        $em = $this->getDoctrine()->getManager();
//        $form1 = $this->createForm('SocialPro\UserBundle\Form\CommentaireType', $commentaire);
//        $form1->handleRequest($request);
        $pub = $em->getRepository("SocialProUserBundle:Publication")->find($idPublication);
//        //var_dump($pub); die();
//       // $pub = new Publication();
//        $pub->getIdPublication();
        if ($request->isMethod("post")) {
            $commentaire->setIdUserCommentaire($this->getUser());
            $date = new \DateTime('now');
            $commentaire->setDateCommentaire($date);
            $commentaire->setIdPublicationCommentaire($pub);
            $commentaire->setContenuCommentaire($request->get('contenuCom'));
            $em->persist($commentaire);
            $em->flush();


        }
        return $this->redirectToRoute('publication_index', array('idCommentaire' => $commentaire->getIdCommentaire()));
//        return $this->render('SocialProUserBundle:publication:index.html.twig', array(
//            'commentaire' => $commentaire,
//            'form1' => $form1->createView(),
//        ));
    }
    /**
     * Creates a new publication entity.
     *
     */
    public function newAction(Request $request)
    {
        /*
    }
        $publication = new Publication();
        $form = $this->createForm('SocialPro\UserBundle\Form\PublicationType', $publication);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $publication->setIdUserPublication($this->getUser());
            $date= new \DateTime('now');
            $publication->setDatePublication($date);
            $em->persist($publication);
            $em->flush();

            return $this->redirectToRoute('publication_show', array('idPublication' => $publication->getIdpublication()));
        }

        return $this->render('SocialProUserBundle:publication:new.html.twig', array(
            'publication' => $publication,
            'form' => $form->createView(),
      ));
       */
        $em = $this->getDoctrine()->getManager();
        $publication = new Publication();
        $commentaire = new Commentaire();
        $form = $this->createForm('SocialPro\UserBundle\Form\PublicationType', $publication);
        $form->handleRequest($request);
        $user = $this->getUser();
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $publication->setIdUserPublication($this->getUser());
            $date= new \DateTime('now');
            $publication->setDatePublication($date);
            $em->persist($publication);
            $em->flush();

            return $this->redirectToRoute('publication_new', array('idPublication' => $publication->getIdpublication()));
        }

        $publications = $em->getRepository('SocialProUserBundle:Publication')->findAll();
        $commentaire = $em->getRepository('SocialProUserBundle:Publication')->findAll();

        $profiles = $em->getRepository('SocialProUserBundle:Profil')->findAll();
        return $this->render('SocialProUserBundle:publication:index.html.twig', array(
            'profiles'=>$profiles,
            'publications' => $publications,'publication' => $publication,
            'form' => $form->createView(),'publication' => $publication,
            'form_insert' => $form->createView(),
            'commentaire' => $commentaire,
            'user'=>$user,'form1' => $form->createView(),
        ));

    }

    /**
     * Finds and displays a publication entity.
     *
     */
    public function showAction(Publication $publication)
    {
        $deleteForm = $this->createDeleteForm($publication);
        return $this->render('SocialProUserBundle:publication:show.html.twig', array(
            'publication' => $publication,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing publication entity.
     *
     */

    public function showallAction(Request $request)
    {
        $user = $this->getUser();
        $em = $this->getDoctrine()->getManager();
        $publication = new Publication();
        $form = $this->createForm('SocialPro\UserBundle\Form\PublicationType', $publication);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($publication);
            $em->flush();

            return $this->redirectToRoute('publication_show', array('idPublication' => $publication->getIdpublication()));
        }

        $publications = $em->getRepository('SocialProUserBundle:Publication')->findAll();

        $profiles = $em->getRepository('SocialProUserBundle:Profil')->findAll();
        return $this->render('SocialProUserBundle:publication:index.html.twig', array(
            'profiles'=>$profiles,
            'publications' => $publications,'publication' => $publication,
            'form' => $form->createView(),'publication' => $publication,
            'form_insert' => $form->createView(),
            'user'=>$user,
        ));
    }
    /**
     * Displays a form to edit an existing publication entity.
     *
     */
    public function editAction(Request $request, Publication $publication)
    {
        //$deleteForm = $this->createDeleteForm($publication);
        $editForm = $this->createForm('SocialPro\UserBundle\Form\PublicationType', $publication);
        $editForm->handleRequest($request);
        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('publication_new');
        }

        return $this->render('SocialProUserBundle:publication:edit.html.twig', array(
            'publication' => $publication,
            'edit_form' => $editForm->createView(),
            //'delete_form' => $deleteForm->createView(),
        ));
    }
}
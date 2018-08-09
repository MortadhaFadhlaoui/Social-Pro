<?php

namespace SocialPro\UserBundle\Controller;

use SocialPro\UserBundle\Entity\Tache;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

/**
 * Tache controller.
 *
 */
class TacheController extends Controller
{
    /**
     * Lists all tache entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $id=$this->getUser();
        $taches = $em->getRepository('SocialProUserBundle:Tache')->findAll();
       // $taches = $em->getRepository('SocialProUserBundle:Tache')->findBy(array('idUser' => $id));
        return $this->render('SocialProUserBundle:tache:index.html.twig', array(
            'taches' => $taches,
        ));
    }

    /**
     * Creates a new tache entity.
     *
     */
    public function newAction(Request $request)
    {
        $tache = new Tache();
        $form = $this->createForm('SocialPro\UserBundle\Form\TacheType', $tache);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($tache);
            $em->flush();

            return $this->redirectToRoute('tache_show', array('idTache' => $tache->getIdtache()));
        }

        return $this->render('SocialProUserBundle:tache:new.html.twig', array(
            'tache' => $tache,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a tache entity.
     *
     */
    public function showAction(Tache $tache)
    {
        $deleteForm = $this->createDeleteForm($tache);

        return $this->render('SocialProUserBundle:tache:show.html.twig', array(
            'tache' => $tache,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing tache entity.
     *
     */
    public function editAction(Request $request, Tache $tache)
    {
        $deleteForm = $this->createDeleteForm($tache);
        $editForm = $this->createForm('SocialPro\UserBundle\Form\TacheType', $tache);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('tache_edit', array('idTache' => $tache->getIdtache()));
        }

        return $this->render('SocialProUserBundle:tache:edit.html.twig', array(
            'tache' => $tache,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a tache entity.
     *
     */
    public function deleteAction(Request $request, Tache $tache)
    {
        $form = $this->createDeleteForm($tache);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($tache);
            $em->flush();
        }

        return $this->redirectToRoute('tache_index');
    }

    /**
     * Creates a form to delete a tache entity.
     *
     * @param Tache $tache The tache entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Tache $tache)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('tache_delete', array('idTache' => $tache->getIdtache())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}

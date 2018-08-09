<?php

namespace SocialPro\ChefBundle\Controller;

use SocialPro\UserBundle\Entity\Projet;
use SocialPro\UserBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class ProjetController extends Controller
{
    public function projetAction()
    {
        $em = $this->getDoctrine()->getManager();
        $projets = $em->getRepository("SocialProUserBundle:Projet")->findByChef($this->getUser());
        return $this->render('SocialProChefBundle:Projet:Projets.html.twig', array("projets" => $projets));
    }

    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $projet = $em->getRepository("SocialProUserBundle:Projet")->find($id);
        $em->remove($projet);
        $em->flush();

        return $this->redirectToRoute('ChefProjets');
    }

    public function addAction(Request $request)
    {
        $projet = new Projet();

        if ($request->isMethod('POST')) {
            $projet->setNom($request->get('nom'));
            $projet->setDescription($request->get('desc'));
            $projet->setDateDebut(new \DateTime($request->get('dated')));
            $projet->setDateFin(new \DateTime($request->get('datef')));
            $projet->setChefProjet($this->getDoctrine()->getManager()->getRepository("SocialProUserBundle:User")->find(12));

            $em = $this->getDoctrine()->getManager();
            $em->persist($projet);
            $em->flush();
            return $this->redirectToRoute('ChefProjets');
        }

        return $this->render('SocialProChefBundle:Projet:Ajout.html.twig', array());
    }

    public function updateAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $projet = $em->getRepository("SocialProUserBundle:Projet")->find($id);

        if ($request->isMethod('POST')) {
            $projet->setNom($request->get('nom'));
            $projet->setDescription($request->get('desc'));
            $projet->setDateDebut(new \DateTime($request->get('dated')));
            $projet->setDateFin(new \DateTime($request->get('datef')));

            $em = $this->getDoctrine()->getManager();
            $em->persist($projet);
            $em->flush();
            return $this->redirectToRoute('ChefProjets');
        }

        return $this->render('SocialProChefBundle:Projet:Update.html.twig', array('projet' => $projet,
            'dated' => date_format($projet->getDateDebut(), 'Y-m-d'),
            'datef' => date_format($projet->getDateFin(), 'Y-m-d')));
    }
    public function pdfAction()
    {
        $em = $this->getDoctrine()->getManager();
        $projets = $em->getRepository("SocialProUserBundle:Projet")->findByChef($this->getUser());

        $html = $this->renderView('SocialProChefBundle:Projet:Demo.html.twig', array("projets" => $projets));

        $filename = "hey";

        return new Response(
            $this->get('knp_snappy.pdf')->getOutputFromHtml($html),
            200,
            array(
                'Content-Type'        => 'application/pdf',
                'Content-Disposition' => 'inline; filename"'.$filename.'.pdf"')

        );
    }
}

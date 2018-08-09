<?php

namespace SocialPro\UserBundle\Controller;

use SocialPro\UserBundle\Entity\Equipe;
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
        $projets = array();
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository("SocialProUserBundle:User")->find(0);
        $equipe = $user->getIdEquipe();
        foreach ($equipe as $e){
            $projets[] = $em->getRepository("SocialProUserBundle:Projet")->find($e->getIdProjet());
        }

        return $this->render('SocialProUserBundle:Projet:List.html.twig', array("projets" => $projets));
    }
    public function pdfAction()
    {
        $projets = array();
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository("SocialProUserBundle:User")->find($this->getUser());
        $equipe = $user->getIdEquipe();
        foreach ($equipe as $e){
            $projets[] = $em->getRepository("SocialProUserBundle:Projet")->find($e->getIdProjet());
        }

        $html = $this->renderView('SocialProUserBundle:Projet:Demo.html.twig', array("projets" => $projets));

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

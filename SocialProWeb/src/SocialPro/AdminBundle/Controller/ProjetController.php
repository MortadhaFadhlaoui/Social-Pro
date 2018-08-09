<?php

namespace SocialPro\AdminBundle\Controller;

use SocialPro\UserBundle\Entity\Equipe;
use SocialPro\UserBundle\Entity\Projet;
use SocialPro\UserBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Ob\HighchartsBundle\Highcharts\Highchart;
use Symfony\Component\HttpFoundation\Response;
use Zend\Json\Expr;


class ProjetController extends Controller
{
    public function projetAction()
    {
        $em = $this->getDoctrine()->getManager();
        $projets = $em->getRepository("SocialProUserBundle:Projet")->findAll();
        return $this->render('AdminBundle:Projet:Projets.html.twig', array("projets" => $projets));
    }

    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $projet = $em->getRepository("SocialProUserBundle:Projet")->find($id);
        $em->remove($projet);
        $em->flush();

        return $this->redirectToRoute('Projets');
    }

    public function addAction(Request $request)
    {
        $projet = new Projet();
        $equipe = new Equipe();

        if ($request->isMethod('POST')) {
            $projet->setNom($request->get('nom'));
            $projet->setDescription($request->get('desc'));
            $projet->setDateDebut(new \DateTime($request->get('dated')));
            $projet->setDateFin(new \DateTime($request->get('datef')));
            $projet->setChefProjet($this->getDoctrine()->getManager()->getRepository("SocialProUserBundle:User")->find($request->get('chef')));

            $equipe->setNom($request->get('equipe'));
            $equipe->setEtat("Actif");
            $projets = $this->getDoctrine()->getManager()->getRepository("SocialProUserBundle:Projet")->findAll();
            $haja = count($projets);
            $equipe->setIdProjet($projets[$haja-1]);


            $em = $this->getDoctrine()->getManager();
            $em->persist($projet);
            $em->flush();


            $em = $this->getDoctrine()->getManager();
            $em->persist($equipe);
            $em->flush();

            $message = \Swift_Message::newInstance()->setSubject('Nouveau Projet')->setFrom('elbluemail@gmail.com')->setTo($projet->getChefProjet()->getEmail())->setBody($this->renderView('AdminBundle:Projet:NotificationMailChefProjet.html.twig', array('projet' => $projet)), 'text/html');
            $this->get('mailer')->send($message);
            return $this->redirectToRoute('Projets');
        }

        $em = $this->getDoctrine()->getManager();
        $chefs = $em->getRepository("SocialProUserBundle:User")->getChefs();

        return $this->render('AdminBundle:Projet:Ajout.html.twig', array("chefs"=>$chefs));
    }

    public function updateAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $projet = $em->getRepository("SocialProUserBundle:Projet")->find($id);

        if($request->isMethod('POST'))
        {
            $projet->setNom($request->get('nom'));
            $projet->setDescription($request->get('desc'));
            $projet->setDateDebut(new \DateTime($request->get('dated')));
            $projet->setDateFin(new \DateTime($request->get('datef')));
            $projet->setChefProjet($this->getDoctrine()->getManager()->getRepository("SocialProUserBundle:User")->find($request->get('chef')));

            $em=$this->getDoctrine()->getManager();
            $em->persist($projet);
            $em->flush();
            $message = \Swift_Message::newInstance()->setSubject('Changement du Projet '.$projet->getNom())->setFrom('elbluemail@gmail.com')->setTo($projet->getChefProjet()->getEmail())->setBody($this->renderView('AdminBundle:Projet:NotificationMailChangmentChefProjet.html.twig', array('projet' => $projet)), 'text/html');
            $this->get('mailer')->send($message);
            return $this->redirectToRoute('Projets');
        }

        $em = $this->getDoctrine()->getManager();
        $chefs = $em->getRepository("SocialProUserBundle:User")->findAll();

        return $this->render('AdminBundle:Projet:Update.html.twig', array('projet'=>$projet,
            'dated'=>date_format($projet->getDateDebut(), 'Y-m-d'),
            'datef'=>date_format($projet->getDateFin(), 'Y-m-d'),
            "chefs"=>$chefs));
    }

    public function chartHistogrammeAction()
    {
        $categories = array();
        $nbEtudiants = array();

        $em = $this->getDoctrine()->getManager();
        $e = $em->getRepository("SocialProUserBundle:Equipe")->findAll();
        foreach ($e as $ee){
            $p = $em->getRepository("SocialProUserBundle:Projet")->find($ee->getIdProjet());
            array_push($categories, $p->getNom());
            $x = 0;
            foreach ($ee->getIdUser() as $u){
                $x = $x +1;
            }
            array_push($nbEtudiants, $x);
        }
        $series = array(array('name' => 'Etudiant', 'type' => 'column', 'color' => '#4572A7', 'yAxis' => 0, 'data' => $nbEtudiants,));
        $yData = array(array('labels' => array('formatter' => new Expr('function () { return this.value + "" }'), 'style' => array('color' => '#4572A7')), 'gridLineWidth' => 0, 'title' => array('text' => 'Nombre des Utilisateurs', 'style' => array('color' => '#4572A7')),),);
        $ob = new Highchart();
        $ob->chart->renderTo('container'); // The #id of the div where to render the chart
        $ob->chart->type('column');
        $ob->title->text('Nombre des Utilisateurs par Projet');
        $ob->xAxis->categories($categories);
        $ob->yAxis($yData);
        $ob->legend->enabled(false);
        $formatter = new Expr('function () { var unit = { "Etudiant": "Utilisateur(s)", }[this.series.name]; return this.x + ": <b>" + this.y + "</b> " + unit; }');
        $ob->tooltip->formatter($formatter);
        $ob->series($series);
        return $this->render('AdminBundle:Projet:Statistique.html.twig', array('chart' => $ob));
    }

    public function pdfAction()
    {
        $em = $this->getDoctrine()->getManager();
        $projets = $em->getRepository("SocialProUserBundle:Projet")->findAll();

        $html = $this->renderView('AdminBundle:Projet:Demo.html.twig', array("projets" => $projets));

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

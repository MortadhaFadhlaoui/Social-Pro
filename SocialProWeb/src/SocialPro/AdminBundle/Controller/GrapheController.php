<?php

namespace SocialPro\AdminBundle\Controller;


use SocialPro\UserBundle\Entity\Profil;
use SocialPro\UserBundle\Entity\User;
use SocialPro\UserBundle\Entity\Proposition;
use SocialPro\UserBundle\Entity\PropositionVote;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Ob\HighchartsBundle\Highcharts\Highchart;
use Zend\Json\Expr;

class GrapheController extends Controller
{
    public function statAction()
    {
        $authChecker = $this->container->get('security.authorization_checker');
        $router = $this->container->get('router');

        if ($authChecker->isGranted('ROLE_ADMIN')) {

            $em = $this->getDoctrine()->getManager();
            $comp=$em->getRepository('SocialProUserBundle:Proposition')->findAll();

            $NomP=array();
            $NbVoteP=array();
            $NbVoteC=array();
            foreach($comp as $prop)
            {
                array_push($NomP,$prop->getTitre());
                $req=$em->getRepository('SocialProUserBundle:PropositionVote')->findBy(array('avis'=>'pour','idProposition'=>$prop->getIdProposition()));
                $req2=$em->getRepository('SocialProUserBundle:PropositionVote')->findBy(array('avis'=>'contre','idProposition'=>$prop->getIdProposition()));
                array_push($NbVoteP,count($req));
                array_push($NbVoteC,count($req2));
            }
            $series = array(
                array(
                    'name' => 'Votes Pour',
                    'type' => 'column',
                    'color' => '#4572A7',
                    'yAxis' => 0,
                    'data' => $NbVoteP,
                ),
                array(
                    'name' => 'Votes Contre',
                    'type' => 'column',
                    'color' => 'red',
                    'yAxis' => 0,
                    'data' => $NbVoteC,
                )
            );
            $yData = array(
                array(
                    'labels' => array(
                        'formatter' => new Expr('function () { return this.value + "" }'),
                        'style' => array('color' => '#4572A7')
                    ),
                    'gridLineWidth' => 0,
                    'title' => array(
                        'text' => 'Nombre des Votes',
                        'style' => array('color' => '#4572A7')
                    ),
                ),
            );

            $ob = new Highchart();
            $ob->chart->renderTo('container'); // The #id of the div
            $ob->chart->type('column');
            $ob->title->text('Nombre des votes par proposition');
            $ob->xAxis->categories($NomP);
            $ob->yAxis($yData);
            $ob->legend->enabled(false);
            $formatter = new Expr('function () {
                        var unit = {
                            "Etudiant": "étudiant(s)",
                             }[this.series.name];
                                return this.x + ": <b>" + this.y + "</b> " + unit;
                            }');
            $ob->tooltip->formatter($formatter);
            $ob->series($series);


            return $this->render('AdminBundle:Graphe:stat.html.twig',array('chart'=>$ob));
        }
        elseif ($authChecker->isGranted('ROLE_USER')) {
            return new RedirectResponse($router->generate('social_pro_user_homepage'), 307);
        }
        else {
            return new RedirectResponse($router->generate('fos_user_security_login'), 307);
        }
    }
}
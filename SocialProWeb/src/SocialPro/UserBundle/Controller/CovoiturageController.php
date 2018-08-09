<?php

namespace SocialPro\UserBundle\Controller;

use SocialPro\UserBundle\Entity\ReservationTrajet;
use SocialPro\UserBundle\Entity\Trajet;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class CovoiturageController extends Controller
{
    public function listAction()
    {
        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->getTrajets($this->getUser());
        return $this->render('SocialProUserBundle:Covoiturage:List.html.twig', array("trajets" => $trajet));
    }

    public function reserveAction($id)
    {
        $rs = new ReservationTrajet();
        $rs->setIdTrajet($this->getDoctrine()->getManager()->getRepository("SocialProUserBundle:Trajet")->find($id));
        $rs->setDateReservation(new \DateTime('now'));
        $rs->setIdUser($this->getDoctrine()->getManager()->getRepository("SocialProUserBundle:User")->find($this->getUser()));

        $em = $this->getDoctrine()->getManager();
        $em->persist($rs);
        $em->flush();

        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->find($id);
        $trajet->setNombrePlace($trajet->getNombrePlace() - 1);
        $em = $this->getDoctrine()->getManager();
        $em->persist($trajet);
        $em->flush();

        return $this->redirectToRoute('Covoiturage');
    }

    public function addAction(Request $request)
    {
        $trajet = new Trajet();

        if ($request->isMethod('POST')) {
            $trajet->setArrive($request->get('arrivee'));
            $trajet->setDate(new \DateTime ($request->get('date')));
            $trajet->setDepart($request->get('depart'));
            $trajet->setHeure(new \DateTime ($request->get('heure')));
            $trajet->setNombrePlace($request->get('place'));
            $trajet->setIdUserTrajet($this->getDoctrine()->getManager()->getRepository("SocialProUserBundle:User")->find($this->getUser()));

            $em = $this->getDoctrine()->getManager();
            $em->persist($trajet);
            $em->flush();
            return $this->redirectToRoute('MesTrajets');
        }

        return $this->render('SocialProUserBundle:Covoiturage:Ajout.html.twig', array());
    }

    public function mineAction()
    {
        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->getUserTrajets($this->getUser());
        return $this->render('SocialProUserBundle:Covoiturage:mine.html.twig', array("trajets" => $trajet));
    }

    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();


        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->find($id);
        $users = $em->getRepository("SocialProUserBundle:ReservationTrajet")->GoingWithYou($id);
        foreach ($users as $u) {
            $message = \Swift_Message::newInstance()->setSubject('Annulation d\'un Trajet ')->setFrom('elbluemail@gmail.com')->setTo($u->getEmail())->setBody($this->renderView('SocialProUserBundle:Covoiturage:NotificationAnnulationTrajet.html.twig', array('user' => $u, 'trajet' => $trajet)), 'text/html');
            $this->get('mailer')->send($message);
        }


        $em->getRepository("SocialProUserBundle:ReservationTrajet")->TrajetDeleted($id);


        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->find($id);
        $em->remove($trajet);
        $em->flush();


        return $this->redirectToRoute('MesTrajets');
    }

    public function updateAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->find($id);

        if ($request->isMethod('POST')) {
            $trajet->setArrive($request->get('arrivee'));
            $trajet->setDate(new \DateTime ($request->get('date')));
            $trajet->setDepart($request->get('depart'));
            $trajet->setHeure(new \DateTime ($request->get('heure')));
            $trajet->setNombrePlace($request->get('place'));

            $em = $this->getDoctrine()->getManager();
            $em->persist($trajet);
            $em->flush();

            $users = $em->getRepository("SocialProUserBundle:ReservationTrajet")->GoingWithYou($id);
            foreach ($users as $u) {
                $message = \Swift_Message::newInstance()->setSubject('Changement d\'un Trajet ')->setFrom('elbluemail@gmail.com')->setTo($u->getEmail())->setBody($this->renderView('SocialProUserBundle:Covoiturage:NotificationMailChangementTrajet.html.twig', array('user' => $u, 'trajet' => $trajet)), 'text/html');
                $this->get('mailer')->send($message);
            }
            return $this->redirectToRoute('MesTrajets');
        }

        return $this->render('SocialProUserBundle:Covoiturage:Update.html.twig', array('trajet' => $trajet,
            'date' => date_format($trajet->getDate(), 'Y-m-d'),
            'heure' => date_format($trajet->getHeure(), 'H:i:s')));
    }

    public function goingAction()
    {
        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->getUserGoingTrajets($this->getUser());

        return $this->render('SocialProUserBundle:Covoiturage:Going.html.twig', array("trajets" => $trajet));
    }

    public function goingWithYouAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $users = $em->getRepository("SocialProUserBundle:ReservationTrajet")->GoingWithYou($id);

        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->find($id);


        return $this->render('SocialProUserBundle:Covoiturage:GoingWithYou.html.twig', array("users" => $users, "trajet" => $trajet));
    }

    public function cancelAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $em->getRepository("SocialProUserBundle:ReservationTrajet")->cancelReservation($this->getUser(), $id);


        $em = $this->getDoctrine()->getManager();
        $trajet = $em->getRepository("SocialProUserBundle:Trajet")->find($id);
        $trajet->setNombrePlace($trajet->getNombrePlace() + 1);
        $em = $this->getDoctrine()->getManager();
        $em->persist($trajet);
        $em->flush();


        return $this->redirectToRoute('TrajetsPartant');
    }

    public function CoviturageAction(Request $request)
    {
        $data = $request->get('input');

        $em = $this->getDoctrine()->getManager();

        $query = $em->createQuery(''
            . 'SELECT t '
            . 'FROM SocialProUserBundle:Trajet t '
            . 'WHERE t.depart LIKE :data or t.arrive LIKE :data AND t.idTrajet NOT IN ( SELECT IDENTITY(r.idTrajet) FROM SocialProUserBundle:ReservationTrajet r WHERE r.idUser =:user )  AND ( t.date > CURRENT_DATE() OR ( t.date = CURRENT_DATE() AND t.heure >= CURRENT_TIME())) AND t.idUserTrajet != :user AND t.nombrePlace > 0'
            . 'ORDER BY t.depart ASC'
        )
            ->setParameter('data', '%' . $data . '%')
            ->setParameter('user', $this->getUser());
        $results = $query->getResult();

        $countryList = '<div id="abc" class="list-group">';
        foreach ($results as $result) {
            $countryList .= '<a class="list-group-item"><button type="button" onclick="location.href=\'http://localhost/social_pro/web/app_dev.php/user/Reserver/' . $result->getIdTrajet() . '\'" class="btn btn-labeled btn-success pull-right spacing"><span class="btn-label"><img src="/social_pro/web/images/checked.png"></span></button>
            <button type="button"
                            onclick="rechercher(\'' . $result->getDepart() . '\',\'' . $result->getArrive() . '\',true); affiche();"
                            class="btn btn-labeled btn-danger pull-right spacing">
                                            <span class="btn-label">
                                                <img src="/social_pro/web/images/placeholder.png">
                                            </span>
                    </button>
                    <button type="button"
                            onclick="rechercher(\'' . $result->getDepart() . '\',\'' . $result->getArrive() . '\',true); affiche2();"
                            class="btn btn-labeled btn-warning pull-right spacing">
                                            <span class="btn-label">
                                                <img src="/social_pro/web/images/google-maps.png">
                                            </span>
                    </button>
                    <p class="list-group-item-text " style="color: #6b6b6b; font-size: 15px;">Départ : <b style="color: #000000;font-size: 20px;">' . $result->getDepart() . '</b> -
                        Arrivée : <b style="color: #000000;font-size: 20px;">' . $result->getArrive() . '</b></p>
                    <p class="list-group-item-text" style="color: #6b6b6b;font-size: 15px;">Places Disponible : <b
                                style="color: #000000;font-size: 20px">' . $result->getNombrePlace() . '</b></p>
                    <span class="label label-primary pull-right" style="font-size: 13px;">Créateur : ' . $result->getIdUserTrajet() . '</span>
                    <p class="list-group-item-text" style="font-size: 15px;">Date de Départ : <b
                                style="color: #000000;font-size: 20px">' . date_format($result->getDate(), 'Y-F-d') . '</b> à <b
                                style="color: #000000;font-size: 20px;">' . date_format($result->getHeure(), 'H:i') . '</b></p>
                </a>';
        }
        $countryList .= '</div>';

        $response = new JsonResponse();
        $response->setData(array('countryList' => $countryList));
        return $response;
    }
}

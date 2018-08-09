<?php
/**
 * Created by PhpStorm.
 * User: Choura
 * Date: 4/1/2017
 * Time: 5:15 PM
 */

namespace SocialPro\UserBundle\Repository;

use Doctrine\ORM\EntityRepository;

class ReservationTrajetRepository extends EntityRepository
{
    public function cancelReservation($user, $trajet)
    {
        $qb=$this->getEntityManager()
            ->createQuery("DELETE FROM SocialProUserBundle:ReservationTrajet r WHERE r.idUser = :user AND r.idTrajet = :trajet")
            ->setParameter('user',$user)
            ->setParameter('trajet',$trajet);

        return $qb->getResult();
    }

    public function TrajetDeleted($trajet)
    {
        $qb=$this->getEntityManager()
            ->createQuery("DELETE FROM SocialProUserBundle:ReservationTrajet r WHERE r.idTrajet = :trajet")
            ->setParameter('trajet',$trajet);

        return $qb->getResult();
    }

    public function GoingWithYou($trajet)
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT u FROM SocialProUserBundle:ReservationTrajet r, SocialProUserBundle:User u WHERE r.idTrajet = :trajet AND u.id = r.idUser")
            ->setParameter('trajet',$trajet);

        return $qb->getResult();
    }
}
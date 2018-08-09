<?php
/**
 * Created by PhpStorm.
 * User: Choura
 * Date: 3/31/2017
 * Time: 4:36 PM
 */

namespace SocialPro\UserBundle\Repository;

use Doctrine\ORM\EntityRepository;

class TrajetRepository extends EntityRepository
{
    public function getTrajets($user)
    {
        $qb=$this->getEntityManager()
            ->createQuery("select t from SocialProUserBundle:Trajet t where t.idTrajet NOT IN ( SELECT IDENTITY(r.idTrajet) FROM SocialProUserBundle:ReservationTrajet r WHERE r.idUser =:user ) AND ( t.date > CURRENT_DATE() OR ( t.date = CURRENT_DATE() AND t.heure >= CURRENT_TIME())) AND t.idUserTrajet != :user AND t.nombrePlace > 0 ORDER  BY t.date")
            ->setParameter('user',$user);

        return $qb->getResult();
    }
    public function getUserTrajets($user)
    {
        $qb=$this->getEntityManager()
            ->createQuery("select t from SocialProUserBundle:Trajet t where  ( t.date > CURRENT_DATE() OR ( t.date = CURRENT_DATE() AND t.heure >= CURRENT_TIME())) AND t.idUserTrajet = :user ORDER  BY t.date")
            ->setParameter('user',$user);

        return $qb->getResult();
    }
    public function getUserGoingTrajets($user)
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT t FROM SocialProUserBundle:Trajet t, SocialProUserBundle:ReservationTrajet r WHERE r.idUser = :user AND r.idTrajet = t.idTrajet AND t.date >= CURRENT_TIMESTAMP() ORDER BY t.date")
            ->setParameter('user',$user);

        return $qb->getResult();
    }
    public function getTrajetsRecherche($user, $rech)
    {
        $qb=$this->getEntityManager()
            ->createQuery("select t from SocialProUserBundle:Trajet t where t.idTrajet NOT IN ( SELECT IDENTITY(r.idTrajet) FROM SocialProUserBundle:ReservationTrajet r WHERE r.idUser =:user ) AND ( t.date > CURRENT_DATE() OR ( t.date = CURRENT_DATE() AND t.heure >= CURRENT_TIME())) AND t.idUserTrajet != :user AND t.nombrePlace > 0 AND t.depart LIKE % :rech % or t.arrive LIKE % :rech % ORDER  BY t.date ")
            ->setParameter('user',$user)
            ->setParameter('rech',$rech);

        return $qb->getResult();
    }
}
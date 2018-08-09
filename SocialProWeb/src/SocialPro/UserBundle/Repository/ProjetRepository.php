<?php
/**
 * Created by PhpStorm.
 * User: Choura
 * Date: 3/31/2017
 * Time: 12:08 AM
 */

namespace SocialPro\UserBundle\Repository;


use Doctrine\ORM\EntityRepository;

class ProjetRepository extends EntityRepository
{
    public function findByChef($chef)
    {
        $qb=$this->getEntityManager()
            ->createQuery("select p from SocialProUserBundle:Projet p where  p.chefProjet=:chef")
            ->setParameter('chef',$chef);

        return $qb->getResult();
    }
    public function findByUserId($user)
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT p FROM SocialProUserBundle:Equipe e, SocialProUserBundle:User u, SocialProUserBundle:Projet p WHERE  u.id = e.idUser AND u.idEquipe = e.idEquipe AND e.idProjet = p.idProjet AND u.id =:userr")
            ->setParameter('userr',$user);

        return $qb->getResult();
    }
}
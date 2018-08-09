<?php
/**
 * Created by PhpStorm.
 * User: Choura
 * Date: 3/31/2017
 * Time: 12:08 AM
 */

namespace SocialPro\AdminBundle\Repository;


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
}
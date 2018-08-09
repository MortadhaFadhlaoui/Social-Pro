<?php
/**
 * Created by PhpStorm.
 * User: Mortadhafff
 * Date: 21/03/2017
 * Time: 18:30
 */

namespace SocialPro\UserBundle\Repository;


use Doctrine\ORM\EntityRepository;

class ProfileRepository extends EntityRepository
{
    public function findProfileByUser($id)
    {
        $query=$this->getEntityManager()->createQuery(
            "select a from SocialProUserBundle:Profil a WHERE a.iduser =:pa"
        )->setParameter('pa',$id);
        return $query->getResult();
    }
    public function FindAllProfile()
    {
        $query=$this->getEntityManager()->createQuery(
            "select u from SocialProUserBundle:Profil u"
        );
        return $query->getResult();
    }
}
<?php
/**
 * Created by PhpStorm.
 * User: ASUS
 * Date: 02/04/2017
 * Time: 00:01
 */

namespace SocialPro\UserBundle\Entity;


use Doctrine\ORM\EntityRepository;

class EvenementRepository extends EntityRepository
{
  public function findEventDQL($id){
      $query=$this->getEntityManager()
          ->createQuery("Select E from SocialProUserBundle:Evenement E WHERE E.idOrganisateur=:id")
      ->setParameter('id',$id);
      return $query->getResult();
  }
    public function deleteInvitDQL($id){
        $query=$this->getEntityManager()-> createQuery('DELETE SocialProUserBundle:Invitation I WHERE I.idEvenement = :id');
        $query->setParameter('id', $id);
        $query->execute();

    }
    public function findInvitDQL($id){
        $query=$this->getEntityManager()
            ->createQuery("Select I from SocialProUserBundle:Invitation I WHERE I.idRecepteur=:id AND I.etat=0")
            ->setParameter('id',$id);
        return $query->getResult();
    }
    public function acceptInvitDQL()
    {
        $query=$this->getEntityManager()
        ->createQuery('SELECT MAX (E.idEvenement) FROM SocialProUserBundle:Evenement E');
        return $query->getScalarResult();
    }
    public function checkInvitDQL($id,$idr)
    {
        $query = $this->getEntityManager()
            ->createQuery('SELECT I  FROM SocialProUserBundle:Invitation I where I.idEvenement= :id and I.idRecepteur= :idr');
        $query->setParameter('id', $id);
        $query->setParameter('idr', $idr);
        return $query->getResult();
    }
    public function findparticipDQL($id){
        $query=$this->getEntityManager()
            ->createQuery("Select P from SocialProUserBundle::ParticiperEvenement P WHERE P.idParticipation=:id")
            ->setParameter('id',$id);
        return $query->getResult();
    }
    public function deleteParticipDQL($id){
        $query=$this->getEntityManager()-> createQuery('DELETE SocialProUserBundle:ParticiperEvenement P WHERE P.idEvenement = :id');
        $query->setParameter('id', $id);
        $query->execute();

    }
    public function deleteRatingDQL($id)
    {
        $query = $this->getEntityManager()->createQuery('DELETE SocialProUserBundle:EvaluationEvent R WHERE R.idEvent = :id');
        $query->setParameter('id', $id);
        $query->execute();
    }

    }
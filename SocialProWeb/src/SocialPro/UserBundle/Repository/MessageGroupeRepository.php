<?php
/**
 * Created by PhpStorm.
 * User: Mortadhafff
 * Date: 09/04/2017
 * Time: 21:50
 */

namespace SocialPro\UserBundle\Repository;


use Doctrine\ORM\EntityRepository;

class MessageGroupeRepository extends EntityRepository
{
    public function getGroupeMessages($user, $addressee)
    {
        $query = $this->getEntityManager()
            ->createQuery('
                SELECT
                m, a, u
                FROM
                SocialProUserBundle:MessageGroupe m
                JOIN
                m.idEmetteur u
                JOIN
                m.idGroupe a
                WHERE
                (m.idEmetteur = :author AND m.idGroupe = :addressee) OR (m.idEmetteur = :addressee AND m.idGroupe = :author)
                ORDER BY m.id
            ');

        $query->setParameters(array('author' => $user, 'addressee' => $addressee));

        return $query->getArrayResult();
    }
    public function getGroupeMessageLongPolling($user, $addressee)
    {
        $em = $this->getEntityManager();

        $query = $em
            ->createQuery('
                SELECT
                m, a, u
                FROM
                SocialProUserBundle:MessageGroupe m
                JOIN
                m.idEmetteur u
                JOIN
                m.idGroupe a
                WHERE
                ((m.idEmetteur = :author AND m.idGroupe = :addressee) OR (m.idEmetteur = :addressee AND m.idGroupe = :author)) AND m.reading = :reading
                ORDER BY m.id
            ');

        $query->setParameters(array('author' => $user, 'addressee' => $addressee, 'reading' => '0'));

        return $query;
    }
    public function DeleteMessageGroupebyUser($user)
    {
        $query = $this->getEntityManager()
            ->createQuery('
                DELETE                
                FROM
                SocialProUserBundle:MessageGroupe m             
                WHERE
                (m.idEmetteur = :author)             
            ');

        $query->setParameters(array('author' => $user));

        return $query->getArrayResult();
    }
    public function DeleteMessageGroupebyGroupe($groupe)
    {
        $query = $this->getEntityManager()
            ->createQuery('
                DELETE                
                FROM
                SocialProUserBundle:MessageGroupe m             
                WHERE
                (m.idGroupe = :addressee)             
            ');

        $query->setParameters(array('addressee' => $groupe));

        return $query->getArrayResult();
    }
}
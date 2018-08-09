<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * PropositionVote
 *
 * @ORM\Table(name="proposition_vote", indexes={@ORM\Index(name="idProposition", columns={"id_proposition"}), @ORM\Index(name="idMembre", columns={"id_membre"})})
 * @ORM\Entity
 */
class PropositionVote
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_proposition", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idProposition;

    /**
     * @var integer
     *
     * @ORM\Column(name="id_membre", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idMembre;

    /**
     * @var string
     *
     * @ORM\Column(name="avis", type="text", length=65535, nullable=false)
     */
    private $avis;

    /**
     * @param string $avis
     */
    public function setAvis($avis)
    {
        $this->avis = $avis;
    }

    /**
     * @return string
     */
    public function getAvis()
    {
        return $this->avis;
    }

    /**
     * @param int $idMembre
     */
    public function setIdMembre($idMembre)
    {
        $this->idMembre = $idMembre;
    }

    /**
     * @return int
     */
    public function getIdMembre()
    {
        return $this->idMembre;
    }

    /**
     * @param int $idProposition
     */
    public function setIdProposition($idProposition)
    {
        $this->idProposition = $idProposition;
    }

    /**
     * @return int
     */
    public function getIdProposition()
    {
        return $this->idProposition;
    }


}


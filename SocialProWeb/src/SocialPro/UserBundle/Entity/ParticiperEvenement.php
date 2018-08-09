<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * ParticiperEvenement
 *
 * @ORM\Table(name="participer_evenement", indexes={@ORM\Index(name="IDX_464C0D916B3CA4B", columns={"id_user"}), @ORM\Index(name="IDX_464C0D918B13D439", columns={"id_evenement"})})
 * @ORM\Entity(repositoryClass="SocialPro\UserBundle\Entity\EvenementRepository")
 */
class ParticiperEvenement
{
    /**
     * @var integer
     ** @ORM\GeneratedValue
     * @ORM\Column(name="id_participation", type="integer")
     * @ORM\Id
     */
    private $idParticipation;

    /**
     * @var \Evenement
     *
     * @ORM\OneToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_evenement", referencedColumnName="id_evenement")
     * })
     */
    private $idEvenement;

    /**
     * @var \User
     *
     * @ORM\OneToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     * })
     */
    private $idUser;

    /**
     * @var integer
     *
     * @ORM\Column(name="etat", type="integer", nullable=false)
     */
    private $etat;

    /**
     * @return int
     */
    public function getEtat()
    {
        return $this->etat;
    }

    /**
     * @param int $etat
     */
    public function setEtat($etat)
    {
        $this->etat = $etat;
    }
    /**
     * @return int
     */
    public function getIdParticipation()
    {
        return $this->idParticipation;
    }

    /**
     * @param int $idParticipation
     */
    public function setIdParticipation($idParticipation)
    {
        $this->idParticipation = $idParticipation;
    }

    /**
     * @return \Evenement
     */
    public function getIdEvenement()
    {
        return $this->idEvenement;
    }

    /**
     * @param \Evenement $idEvenement
     */
    public function setIdEvenement($idEvenement)
    {
        $this->idEvenement = $idEvenement;
    }

    /**
     * @return \User
     */
    public function getIdUser()
    {
        return $this->idUser;
    }

    /**
     * @param \User $idUser
     */
    public function setIdUser($idUser)
    {
        $this->idUser = $idUser;
    }


}


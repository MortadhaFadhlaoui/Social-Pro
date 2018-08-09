<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Trajet
 *
 * @ORM\Table(name="trajet", indexes={@ORM\Index(name="idUser", columns={"id_user_trajet"})})
 * @ORM\Entity(repositoryClass="SocialPro\UserBundle\Repository\TrajetRepository")
 */
class Trajet
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_trajet", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idTrajet;

    /**
     * @var string
     *
     * @ORM\Column(name="depart", type="text", length=65535, nullable=false)
     */
    private $depart;

    /**
     * @var string
     *
     * @ORM\Column(name="arrive", type="text", length=65535, nullable=false)
     */
    private $arrive;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date", type="date", nullable=false)
     */
    private $date;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="heure", type="time", nullable=false)
     */
    private $heure;

    /**
     * @var integer
     *
     * @ORM\Column(name="nombre_place", type="integer", nullable=false)
     */
    private $nombrePlace;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user_trajet", referencedColumnName="id")
     * })
     */
    private $idUserTrajet;



    /**
     * Get idTrajet
     *
     * @return integer
     */
    public function getIdTrajet()
    {
        return $this->idTrajet;
    }

    /**
     * Set depart
     *
     * @param string $depart
     *
     * @return Trajet
     */
    public function setDepart($depart)
    {
        $this->depart = $depart;

        return $this;
    }

    /**
     * Get depart
     *
     * @return string
     */
    public function getDepart()
    {
        return $this->depart;
    }

    /**
     * Set arrive
     *
     * @param string $arrive
     *
     * @return Trajet
     */
    public function setArrive($arrive)
    {
        $this->arrive = $arrive;

        return $this;
    }

    /**
     * Get arrive
     *
     * @return string
     */
    public function getArrive()
    {
        return $this->arrive;
    }

    /**
     * Set date
     *
     * @param \DateTime $date
     *
     * @return Trajet
     */
    public function setDate($date)
    {
        $this->date = $date;

        return $this;
    }

    /**
     * Get date
     *
     * @return \DateTime
     */
    public function getDate()
    {
        return $this->date;
    }

    /**
     * Set heure
     *
     * @param \DateTime $heure
     *
     * @return Trajet
     */
    public function setHeure($heure)
    {
        $this->heure = $heure;

        return $this;
    }

    /**
     * Get heure
     *
     * @return \DateTime
     */
    public function getHeure()
    {
        return $this->heure;
    }

    /**
     * Set nombrePlace
     *
     * @param integer $nombrePlace
     *
     * @return Trajet
     */
    public function setNombrePlace($nombrePlace)
    {
        $this->nombrePlace = $nombrePlace;

        return $this;
    }

    /**
     * Get nombrePlace
     *
     * @return integer
     */
    public function getNombrePlace()
    {
        return $this->nombrePlace;
    }

    /**
     * Set idUserTrajet
     *
     * @param \SocialPro\UserBundle\Entity\User $idUserTrajet
     *
     * @return Trajet
     */
    public function setIdUserTrajet(\SocialPro\UserBundle\Entity\User $idUserTrajet = null)
    {
        $this->idUserTrajet = $idUserTrajet;

        return $this;
    }

    /**
     * Get idUserTrajet
     *
     * @return \SocialPro\UserBundle\Entity\User
     */
    public function getIdUserTrajet()
    {
        return $this->idUserTrajet;
    }
}

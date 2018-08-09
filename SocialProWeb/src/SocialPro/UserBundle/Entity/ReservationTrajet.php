<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * ReservationTrajet
 *
 * @ORM\Table(name="reservation_trajet", indexes={@ORM\Index(name="IDX_E21C95B7D6C1C61", columns={"id_trajet"}), @ORM\Index(name="IDX_E21C95B76B3CA4B", columns={"id_user"})})
 * @ORM\Entity(repositoryClass="SocialPro\UserBundle\Repository\ReservationTrajetRepository")
 */
class ReservationTrajet
{
    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_reservation", type="date", nullable=true)
     */
    private $dateReservation;

    /**
     * @var \Trajet
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="Trajet")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_trajet", referencedColumnName="id_trajet")
     * })
     */
    private $idTrajet;

    /**
     * @var \User
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     * })
     */
    private $idUser;



    /**
     * Set dateReservation
     *
     * @param \DateTime $dateReservation
     *
     * @return ReservationTrajet
     */
    public function setDateReservation($dateReservation)
    {
        $this->dateReservation = $dateReservation;

        return $this;
    }

    /**
     * Get dateReservation
     *
     * @return \DateTime
     */
    public function getDateReservation()
    {
        return $this->dateReservation;
    }

    /**
     * Set idTrajet
     *
     * @param \SocialPro\UserBundle\Entity\Trajet $idTrajet
     *
     * @return ReservationTrajet
     */
    public function setIdTrajet(\SocialPro\UserBundle\Entity\Trajet $idTrajet)
    {
        $this->idTrajet = $idTrajet;

        return $this;
    }

    /**
     * Get idTrajet
     *
     * @return \SocialPro\UserBundle\Entity\Trajet
     */
    public function getIdTrajet()
    {
        return $this->idTrajet;
    }

    /**
     * Set idUser
     *
     * @param \SocialPro\UserBundle\Entity\User $idUser
     *
     * @return ReservationTrajet
     */
    public function setIdUser(\SocialPro\UserBundle\Entity\User $idUser)
    {
        $this->idUser = $idUser;

        return $this;
    }

    /**
     * Get idUser
     *
     * @return \SocialPro\UserBundle\Entity\User
     */
    public function getIdUser()
    {
        return $this->idUser;
    }
}

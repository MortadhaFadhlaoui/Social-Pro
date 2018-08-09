<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Invitation
 *
 * @ORM\Table(name="invitation", indexes={@ORM\Index(name="idEvenement", columns={"id_evenement"}), @ORM\Index(name="idRecepteur", columns={"id_recepteur"}), @ORM\Index(name="idEmetteur", columns={"id_emetteur"})})
 * @ORM\Entity(repositoryClass="SocialPro\UserBundle\Entity\EvenementRepository")
 */
class Invitation
{
    /**
     * @ORM\GeneratedValue
     * @var integer
     * @ORM\Column(name="id_invitation", type="integer")
     * @ORM\Id
     *
     */
    private $idInvitation;



    /**
     * @var \User
     *
     *
     * @ORM\OneToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_emetteur", referencedColumnName="id")
     * })
     */
    private $idEmetteur;

    /**
     * @var \User
     *
     *
     * @ORM\OneToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_recepteur", referencedColumnName="id")
     * })
     */
    private $idRecepteur;

    /**
     * @var \Evenement
     *
     *
     * @ORM\OneToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_evenement", referencedColumnName="id_evenement")
     * })
     */
    private $idEvenement;
    /**
     * @var string
     *
     * @ORM\Column(name="contenu", type="text", length=65535, nullable=false)
     */
    private $contenu;

    /**
     * @var integer
     *
     * @ORM\Column(name="etat", type="integer", nullable=false)
     */
    private $etat;

    /**
     * @return int
     */
    public function getIdInvitation()
    {
        return $this->idInvitation;
    }

    /**
     * @param int $idInvitation
     */
    public function setIdInvitation($idInvitation)
    {
        $this->idInvitation = $idInvitation;
    }

    /**
     * @return string
     */
    public function getContenu()
    {
        return $this->contenu;
    }

    /**
     * @param string $contenu
     */
    public function setContenu($contenu)
    {
        $this->contenu = $contenu;
    }

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
     * @return \User
     */
    public function getIdEmetteur()
    {
        return $this->idEmetteur;
    }

    /**
     * @param \User $idEmetteur
     */
    public function setIdEmetteur($idEmetteur)
    {
        $this->idEmetteur = $idEmetteur;
    }

    /**
     * @return \User
     */
    public function getIdRecepteur()
    {
        return $this->idRecepteur;
    }

    /**
     * @param \User $idRecepteur
     */
    public function setIdRecepteur($idRecepteur)
    {
        $this->idRecepteur = $idRecepteur;
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


}


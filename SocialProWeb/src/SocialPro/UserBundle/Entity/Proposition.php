<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Proposition
 *
 * @ORM\Table(name="proposition", indexes={@ORM\Index(name="idMembre", columns={"id_membre_proposition"})})
 * @ORM\Entity
 */
class Proposition
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_proposition", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idProposition;

    /**
     * @var string
     *
     * @ORM\Column(name="titre", type="text", length=65535, nullable=false)
     */
    private $titre;

    /**
     * @var string
     *
     * @ORM\Column(name="sujet", type="text", length=65535, nullable=false)
     */
    private $sujet;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_membre_proposition", referencedColumnName="id")
     * })
     */
    private $idMembreProposition;

    /**
     * @param \User $idMembreProposition
     */
    public function setIdMembreProposition($idMembreProposition)
    {
        $this->idMembreProposition = $idMembreProposition;
    }

    /**
     * @return \User
     */
    public function getIdMembreProposition()
    {
        return $this->idMembreProposition;
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

    /**
     * @param string $sujet
     */
    public function setSujet($sujet)
    {
        $this->sujet = $sujet;
    }

    /**
     * @return string
     */
    public function getSujet()
    {
        return $this->sujet;
    }

    /**
     * @param string $titre
     */
    public function setTitre($titre)
    {
        $this->titre = $titre;
    }

    /**
     * @return string
     */
    public function getTitre()
    {
        return $this->titre;
    }


}


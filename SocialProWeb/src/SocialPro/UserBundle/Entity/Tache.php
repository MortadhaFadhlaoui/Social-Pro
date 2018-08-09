<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use SocialPro\UserBundle\Entity\Competence;

/**
 * Tache
 *
 * @ORM\Table(name="tache", indexes={@ORM\Index(name="idCompetence", columns={"id_competence"})})
 * @ORM\Entity
 */
class Tache
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_tache", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idTache;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="text", length=65535, nullable=false)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text", length=65535, nullable=false)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="complexite", type="text", length=65535, nullable=false)
     */
    private $complexite;

    /**
     * @var Competence
     *
     * @ORM\ManyToOne(targetEntity="Competence")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_competence", referencedColumnName="id_competence")
     * })
     */
    private $idCompetence;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="User", mappedBy="idTache")
     */
    private $idUser;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->idUser = new \Doctrine\Common\Collections\ArrayCollection();
    }


    /**
     * Get idTache
     *
     * @return integer
     */
    public function getIdTache()
    {
        return $this->idTache;
    }

    /**
     * Set nom
     *
     * @param string $nom
     *
     * @return Tache
     */
    public function setNom($nom)
    {
        $this->nom = $nom;

        return $this;
    }

    /**
     * Get nom
     *
     * @return string
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * Set description
     *
     * @param string $description
     *
     * @return Tache
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Set complexite
     *
     * @param string $complexite
     *
     * @return Tache
     */
    public function setComplexite($complexite)
    {
        $this->complexite = $complexite;

        return $this;
    }

    /**
     * Get complexite
     *
     * @return string
     */
    public function getComplexite()
    {
        return $this->complexite;
    }

    /**
     * Set idCompetence
     *
     * @param \SocialPro\UserBundle\Entity\Competence $idCompetence
     *
     * @return Tache
     */
    public function setIdCompetence(\SocialPro\UserBundle\Entity\Competence $idCompetence = null)
    {
        $this->idCompetence = $idCompetence;

        return $this;
    }

    /**
     * Get idCompetence
     *
     * @return \SocialPro\UserBundle\Entity\Competence
     */
    public function getIdCompetence()
    {
        return $this->idCompetence;
    }

    /**
     * Add idUser
     *
     * @param \SocialPro\UserBundle\Entity\User $idUser
     *
     * @return Tache
     */
    public function addIdUser(\SocialPro\UserBundle\Entity\User $idUser)
    {
        $this->idUser[] = $idUser;

        return $this;
    }

    /**
     * Remove idUser
     *
     * @param \SocialPro\UserBundle\Entity\User $idUser
     */
    public function removeIdUser(\SocialPro\UserBundle\Entity\User $idUser)
    {
        $this->idUser->removeElement($idUser);
    }

    /**
     * Get idUser
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdUser()
    {
        return $this->idUser;
    }
}

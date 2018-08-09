<?php

namespace SocialPro\UserBundle\Entity;

use FOS\UserBundle\Model\User as BaseUser;
use Doctrine\ORM\Mapping as ORM;

/**
 * User
 *
 * @ORM\Table(name="user", uniqueConstraints={@ORM\UniqueConstraint(name="UNIQ_8D93D64992FC23A8", columns={"username_canonical"}), @ORM\UniqueConstraint(name="UNIQ_8D93D649A0D96FBF", columns={"email_canonical"})})
 * @ORM\Entity(repositoryClass="SocialPro\UserBundle\Repository\UserRepository")
 */
class User extends BaseUser
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    protected $id;

    /**
     * @var string
     *
     * @ORM\Column(name="cin", type="string", length=255, nullable=false)
     */
    private $cin;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=255, nullable=false)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="prenom", type="string", length=255, nullable=false)
     */
    private $prenom;

    /**
     * @var string
     *
     * @ORM\Column(name="statut", type="string", length=255, nullable=false ,options={"default" : "Libre"})
     */
    private $statut = "Libre";

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Competence", mappedBy="idUser")
     */
    private $idCompetence;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Equipe", mappedBy="idUser")
     */
    private $idEquipe;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Tache", inversedBy="idUser")
     * @ORM\JoinTable(name="membre_tache",
     *   joinColumns={
     *     @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="id_tache", referencedColumnName="id_tache")
     *   }
     * )
     */
    private $idTache;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Publication", mappedBy="idUserInteressante")
     */
    private $idPublication;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Groupe", mappedBy="idUser")
     */
    private $idGroupe;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="lastActivity", type="datetime", nullable=false)
     */
    private $lastActivity;

    /**
     * @var string
     *
     * @ORM\Column(name="role", type="string", length=255, nullable=false)
     */
    private $role;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Profil", mappedBy="idUser")
     */
    private $idProfil;

    /**
     * Constructor
     */
    public function __construct()
    {
        parent::__construct();
        $this->idCompetence = new \Doctrine\Common\Collections\ArrayCollection();
        $this->idEquipe = new \Doctrine\Common\Collections\ArrayCollection();
        $this->idTache = new \Doctrine\Common\Collections\ArrayCollection();
        $this->idPublication = new \Doctrine\Common\Collections\ArrayCollection();
        $this->idGroupe = new \Doctrine\Common\Collections\ArrayCollection();
        $this->idProfil = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * @return string
     */
    public function getRole()
    {
        return $this->role;
    }


    /**
     * @param string $role
     */
    public function setRole($role)
    {
        $this->role = $role;
    }

    /**
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdProfil()
    {
        return $this->idProfil;
    }

    /**
     * @param \Doctrine\Common\Collections\Collection $idProfil
     */
    public function setIdProfil($idProfil)
    {
        $this->idProfil = $idProfil;
    }


    /**
     * @return string
     */
    public function getCin()
    {
        return $this->cin;
    }

    /**
     * @param string $cin
     */
    public function setCin($cin)
    {
        $this->cin = $cin;
    }

    /**
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param int $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }

    /**
     * @return string
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * @param string $nom
     */
    public function setNom($nom)
    {
        $this->nom = $nom;
    }

    /**
     * @return string
     */
    public function getPrenom()
    {
        return $this->prenom;
    }

    /**
     * @param string $prenom
     */
    public function setPrenom($prenom)
    {
        $this->prenom = $prenom;
    }

    /**
     * @return string
     */
    public function getStatut()
    {
        return $this->statut;
    }

    /**
     * @param string $statut
     */
    public function setStatut($statut)
    {
        $this->statut = $statut;
    }

    /**
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdCompetence()
    {
        return $this->idCompetence;
    }

    /**
     * @param \Doctrine\Common\Collections\Collection $idCompetence
     */
    public function setIdCompetence($idCompetence)
    {
        $this->idCompetence = $idCompetence;
    }

    /**
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdEquipe()
    {
        return $this->idEquipe;
    }

    /**
     * @param \Doctrine\Common\Collections\Collection $idEquipe
     */
    public function setIdEquipe($idEquipe)
    {
        $this->idEquipe = $idEquipe;
    }

    /**
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdTache()
    {
        return $this->idTache;
    }

    /**
     * @param \Doctrine\Common\Collections\Collection $idTache
     */
    public function setIdTache($idTache)
    {
        $this->idTache = $idTache;
    }

    /**
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdPublication()
    {
        return $this->idPublication;
    }

    /**
     * @param \Doctrine\Common\Collections\Collection $idPublication
     */
    public function setIdPublication($idPublication)
    {
        $this->idPublication = $idPublication;
    }

    /**
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdGroupe()
    {
        return $this->idGroupe;
    }

    /**
     * @param \Doctrine\Common\Collections\Collection $idGroupe
     */
    public function setIdGroupe($idGroupe)
    {
        $this->idGroupe = $idGroupe;
    }

    public function addEquipe(Equipe $e = null)
    {
        $this->idEquipe->add($e);
    }

    public function removeEquipe(Equipe $e)
    {
        $this->idEquipe->removeElement($e);
        $this->statut = 'Libre';
    }

    /**
     * @return \DateTime
     */
    public function getLastActivity()
    {
        return $this->lastActivity;
    }

    /**
     * @param \DateTime $lastActivity
     */
    public function setLastActivity($lastActivity)
    {
        $this->lastActivity = $lastActivity;
    }

    public function isActiveNow()
    {
        $this->setLastActivity(new \DateTime());
    }
}


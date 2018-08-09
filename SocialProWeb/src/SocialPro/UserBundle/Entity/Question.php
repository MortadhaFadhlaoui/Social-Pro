<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Question
 *
 * @ORM\Table(name="question", indexes={@ORM\Index(name="idUser", columns={"id_user"})})
 * @ORM\Entity
 */
class Question
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_question", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idQuestion;

    /**
     * @var integer
     *
     * @ORM\Column(name="titre", type="integer", nullable=false)
     */
    private $titre;

    /**
     * @var integer
     *
     * @ORM\Column(name="sujet", type="integer", nullable=false)
     */
    private $sujet;

    /**
     * @var string
     *
     * @ORM\Column(name="statut", type="text", length=65535, nullable=false)
     */
    private $statut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_question", type="datetime", nullable=false)
     */
    private $dateQuestion;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     * })
     */
    private $idUser;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Competence", mappedBy="idQuestion")
     */
    private $idCompetence;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->idCompetence = new \Doctrine\Common\Collections\ArrayCollection();
    }

}


<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Competence
 *
 * @ORM\Table(name="competence")
 * @ORM\Entity
 */
class Competence
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_competence", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idCompetence;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="text", length=65535, nullable=false)
     */
    private $nom;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="User", inversedBy="idCompetence")
     * @ORM\JoinTable(name="competence_user",
     *   joinColumns={
     *     @ORM\JoinColumn(name="id_competence", referencedColumnName="id_competence")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     *   }
     * )
     */
    private $idUser;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Question", inversedBy="idCompetence")
     * @ORM\JoinTable(name="question_competence",
     *   joinColumns={
     *     @ORM\JoinColumn(name="id_competence", referencedColumnName="id_competence")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="id_question", referencedColumnName="id_question")
     *   }
     * )
     */
    private $idQuestion;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->idUser = new \Doctrine\Common\Collections\ArrayCollection();
        $this->idQuestion = new \Doctrine\Common\Collections\ArrayCollection();
    }

}


<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * SignalisationCommentaire
 *
 * @ORM\Table(name="signalisation_commentaire", indexes={@ORM\Index(name="idCommentaire", columns={"id_commentaire"}), @ORM\Index(name="idUser", columns={"id_user"})})
 * @ORM\Entity
 */
class SignalisationCommentaire
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_commentaire", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idCommentaire;

    /**
     * @var integer
     *
     * @ORM\Column(name="id_user", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idUser;

    /**
     * @var string
     *
     * @ORM\Column(name="raison", type="text", length=65535, nullable=false)
     */
    private $raison;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_signalisation", type="date", nullable=false)
     */
    private $dateSignalisation;


}


<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * SignalisationPublication
 *
 * @ORM\Table(name="signalisation_publication", indexes={@ORM\Index(name="idPublication", columns={"id_publication"}), @ORM\Index(name="idUser", columns={"id_user"})})
 * @ORM\Entity
 */
class SignalisationPublication
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_publication", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idPublication;

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


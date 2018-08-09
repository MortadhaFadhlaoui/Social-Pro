<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Publication
 *
 * @ORM\Table(name="publication", indexes={@ORM\Index(name="idUser", columns={"id_user_publication"})})
 * @ORM\Entity
 */
class Publication
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_publication", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idPublication;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_publication", type="datetime", nullable=false)
     */
    private $datePublication;

    /**
     * @var string
     *
     * @ORM\Column(name="contenu_publication", type="text", length=65535, nullable=false)
     */
    private $contenuPublication;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user_publication", referencedColumnName="id")
     * })
     */
    private $idUserPublication;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="User", inversedBy="idPublication")
     * @ORM\JoinTable(name="publication_interessante",
     *   joinColumns={
     *     @ORM\JoinColumn(name="id_publication", referencedColumnName="id_publication")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="id_user_interessante", referencedColumnName="id")
     *   }
     * )
     */
    private $idUserInteressante;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->idUserInteressante = new \Doctrine\Common\Collections\ArrayCollection();
    }


    /**
     * Get idPublication
     *
     * @return integer
     */
    public function getIdPublication()
    {
        return $this->idPublication;
    }

    /**
     * Set datePublication
     *
     * @param \DateTime $datePublication
     *
     * @return Publication
     */
    public function setDatePublication($datePublication)
    {
        $this->datePublication = $datePublication;

        return $this;
    }

    /**
     * Get datePublication
     *
     * @return \DateTime
     */
    public function getDatePublication()
    {
        return $this->datePublication;
    }

    /**
     * Set contenuPublication
     *
     * @param string $contenuPublication
     *
     * @return Publication
     */
    public function setContenuPublication($contenuPublication)
    {
        $this->contenuPublication = $contenuPublication;

        return $this;
    }

    /**
     * Get contenuPublication
     *
     * @return string
     */
    public function getContenuPublication()
    {
        return $this->contenuPublication;
    }

    /**
     * Set idUserPublication
     *
     * @param \SocialPro\UserBundle\Entity\User $idUserPublication
     *
     * @return Publication
     */
    public function setIdUserPublication(\SocialPro\UserBundle\Entity\User $idUserPublication = null)
    {
        $this->idUserPublication = $idUserPublication;

        return $this;
    }

    /**
     * Get idUserPublication
     *
     * @return \SocialPro\UserBundle\Entity\User
     */
    public function getIdUserPublication()
    {
        return $this->idUserPublication;
    }

    /**
     * Add idUserInteressante
     *
     * @param \SocialPro\UserBundle\Entity\User $idUserInteressante
     *
     * @return Publication
     */
    public function addIdUserInteressante(\SocialPro\UserBundle\Entity\User $idUserInteressante)
    {
        $this->idUserInteressante[] = $idUserInteressante;

        return $this;
    }

    /**
     * Remove idUserInteressante
     *
     * @param \SocialPro\UserBundle\Entity\User $idUserInteressante
     */
    public function removeIdUserInteressante(\SocialPro\UserBundle\Entity\User $idUserInteressante)
    {
        $this->idUserInteressante->removeElement($idUserInteressante);
    }

    /**
     * Get idUserInteressante
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdUserInteressante()
    {
        return $this->idUserInteressante;
    }
}

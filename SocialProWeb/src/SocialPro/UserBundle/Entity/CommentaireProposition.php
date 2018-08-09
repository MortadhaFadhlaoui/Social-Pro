<?php

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;


use Symfony\Component\Security\Core\User\UserInterface;
/**
 * CommentaireProposition
 *
 * @ORM\Table(name="commentaire_proposition", indexes={@ORM\Index(name="IDX_1CB401ABDFDF0EA4", columns={"id_proposition"}), @ORM\Index(name="id_proposition", columns={"id_proposition", "id_user"}), @ORM\Index(name="id_user", columns={"id_user"})})
 * @ORM\Entity
 */
class CommentaireProposition
{
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
     * @ORM\Id
     * @ORM\Column(type="integer")
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    protected  $idCommentaire;

    /**
     * @var \Proposition
     *
     * @ORM\ManyToOne(targetEntity="Proposition")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_proposition", referencedColumnName="id_proposition")
     * })
     */
    private $idProposition;
    /**
     * @var string
     *
     * @ORM\Column(name="text", type="string", length=255, nullable=false)
     */
    private $text;

    /**
     * @param mixed $idCommentaire
     */
    public function setIdCommentaire($idCommentaire)
    {
        $this->idCommentaire = $idCommentaire;
    }

    /**
     * @return mixed
     */
    public function getIdCommentaire()
    {
        return $this->idCommentaire;
    }

    /**
     * @param \Proposition $idProposition
     */
    public function setIdProposition($idProposition)
    {
        $this->idProposition = $idProposition;
    }

    /**
     * @return \Proposition
     */
    public function getIdProposition()
    {
        return $this->idProposition;
    }

    /**
     * @param \User $idUser
     */
    public function setIdUser($idUser)
    {
        $this->idUser = $idUser;
    }

    /**
     * @return \User
     */
    public function getIdUser()
    {
        return $this->idUser;
    }

    /**
     * @param string $text
     */
    public function setText($text)
    {
        $this->text = $text;
    }

    /**
     * @return string
     */
    public function getText()
    {
        return $this->text;
    }



}


<?php
/**
 * Created by PhpStorm.
 * User: Mortadhafff
 * Date: 02/04/2017
 * Time: 23:24
 */

namespace SocialPro\UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * UserGroup
 *
 * @ORM\Table(name="user_groupe", indexes={@ORM\Index(name="idGroupe", columns={"id_groupe"}), @ORM\Index(name="idUser", columns={"id_user"})})
 * @ORM\Entity
 */
class UserGroup
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_groupe", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idGroupe;

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
     * @ORM\Column(name="vu", type="string",length=255,nullable=true)
     */
    protected $vu;

    /**
     * @return int
     */
    public function getIdGroupe()
    {
        return $this->idGroupe;
    }

    /**
     * @param int $idGroupe
     */
    public function setIdGroupe($idGroupe)
    {
        $this->idGroupe = $idGroupe;
    }

    /**
     * @return int
     */
    public function getIdUser()
    {
        return $this->idUser;
    }

    /**
     * @param int $idUser
     */
    public function setIdUser($idUser)
    {
        $this->idUser = $idUser;
    }

    /**
     * @return string
     */
    public function getVu()
    {
        return $this->vu;
    }

    /**
     * @param string $vu
     */
    public function setVu($vu)
    {
        $this->vu = $vu;
    }

}
<?php
/**
 * Created by PhpStorm.
 * User: Mortadhafff
 * Date: 28/03/2017
 * Time: 13:47
 */

namespace SocialPro\UserBundle\Controller;


use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class AjaxAutocompleteController extends Controller
{
    public function updateDataAction(Request $request)
    {
        $currentUser = $this->getUser();
        $data = $request->get('input');
        $em = $this->getDoctrine()->getManager();

        $query = $em->createQuery(''
            . 'SELECT c.id, c.username , c.email '
            . 'FROM SocialProUserBundle:User c '
            . 'WHERE c.username LIKE :data '
            . 'ORDER BY c.username ASC'
        )
            ->setParameter('data', '%' . $data . '%');
        $results = $query->getResult();
        $usersList = '<div id="friends" class="friendsforsearch">';
        foreach ($results as $result) {
            if ($result['id'] !=$currentUser->getId())
            {
                $profile = $em->getRepository('SocialProUserBundle:Profil')->findOneBy(array('iduser'=>$result['id']));
                if (!empty($profile) and !empty($profile->getImageName())) {
                    $usersList .= '<div onclick="return false;" data-number="' . $result['id'] . '" class="friend"><img src="/Social_Pro/web/img/authors/' . $profile->getImageName() . '" /><strong>' . $result['username'] . '</strong><br><span>' . $result['email'] . '</span></div></div>';
                }
                else
                {
                    $usersList .= '<div onclick="return false;" data-number="'.$result['id'].'" class="friend"><img src="/Social_Pro/web/img/authors/user-avatar-placeholder.png" /><strong>'.$result['username'].'</strong><br><span>'.$result['email'].'</span></div>';
                }
            }

        }
        $usersList .= '</div>';
        $response = new JsonResponse();
        $response->setData(array('usersList' => $usersList));
        return $response;
    }
}
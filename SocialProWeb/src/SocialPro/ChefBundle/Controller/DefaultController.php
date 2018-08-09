<?php

namespace SocialPro\ChefBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('@SocialProChef/master.html.twig');
    }
}

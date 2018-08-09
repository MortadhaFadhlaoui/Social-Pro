<?php

namespace SocialPro\UserBundle;

use Symfony\Component\HttpKernel\Bundle\Bundle;

class SocialProUserBundle extends Bundle
{
    public function getParent()
    {
        return  'FOSUserBundle';
    }
}

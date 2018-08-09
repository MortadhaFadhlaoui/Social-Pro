<?php
/**
 * Created by PhpStorm.
 * User: ASUS
 * Date: 02/04/2017
 * Time: 12:30
 */

namespace SocialPro\UserBundle\Form;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

class InviterForm extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder

            ->add('idRecepteur',EntityType::class, array('class'=> 'SocialProUserBundle:User','choice_label'=>'nom',
                'multiple'=>false,   ))
            ->add('Inviter',SubmitType::class);
        ;
    }
}
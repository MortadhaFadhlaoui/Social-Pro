<?php
/**
 * Created by PhpStorm.
 * User: ASUS
 * Date: 01/04/2017
 * Time: 22:34
 */

namespace  SocialPro\UserBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

class rechercheForm  extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {

            $builder
                ->add('nom', TextType::class)
                ->add('rechercher',SubmitType::class);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
    }
    public function getName()
    {
        return 'userbundle_evenement_recherche';
    }

}
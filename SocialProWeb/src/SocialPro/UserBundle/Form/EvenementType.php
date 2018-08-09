<?php

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


class EvenementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
       ->add('nom', TextType::class)
            ->add('date',DateType::Class, array(
                'widget' => 'single_text',
                'attr' => array(
                    'min' => (new \DateTime('now'))->format('c'),
                )))

            ->add('lieu', TextType::class)
            ->add('confidentialite', ChoiceType::class,array('choices'=>array('prive'=>'prive','Public'=>'Public')))
            ->add('type', ChoiceType::class,array('choices'=>array('Professionnelle'=>'Profesionelle','loisir'=>'loisir')))
            ->add('description',TextareaType::class)
            ->add('OK',SubmitType::class);
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
    }
    public function getName()
    {
        return 'userbundle_evenement';
    }



}

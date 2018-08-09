<?php

namespace SocialPro\UserBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;


class updateEventForm extends AbstractType
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
            ->add('confidentialite', ChoiceType::class,array('choices'=>array('prive'=>'prive','public'=>'public')))
            ->add('type', ChoiceType::class,array('choices'=>array('professionnelle'=>'profesionelle','loisir'=>'loisir')))
            ->add('description',TextareaType::class)
            ->add('OK',SubmitType::class);
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {

    }

    public function getBlockPrefix()
    {
        return 'social_pro_user_bundleupdate_event_form';
    }
}

/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.myapp;

import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.util.Resources;
import utils.Context;

/**
 * Utility methods common to forms e.g. for binding the side menu
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public void installSidemenu(Resources res) {
        Image selection = res.getImage("selection-in-sidemenu.png");

        Image accueilImage = null;
        if (isCurrentAccueil()) {
            accueilImage = selection;
        }

        Image calendarImage = null;
        if (isCurrentForum()) {
            calendarImage = selection;
        }

        Image propImage = null;
        if (isCurrentProposition()) {
            propImage = selection;
        }

        Image evenementImage = null;
        if (isCurrentEvenement()) {
            evenementImage = selection;
        }

        Image covoiturageImage = null;
        if (isCurrentCovoiturage()) {
            covoiturageImage = selection;
        }

        Image chatImage = null;
        if (isCurrentChatForm()) {
            chatImage = selection;
        }

        Image profilImage = null;
        if (isCurrentProfil()) {
            profilImage = selection;
        }

        Image propositionImage = null;
        if (isCurrentShwoPub()) {
            propositionImage = selection;
        }

        Image showFImage = null;
        if (isCurrentShwoF()) {
            showFImage = selection;
        }

        Image showTImage = null;
        if (isCurrentShwoT()) {
            showTImage = selection;
        }

        Image showSTImage = null;
        if (isCurrentShwoST()) {
            showSTImage = selection;
        }

        Image showSFImage = null;
        if (isCurrentShwoSF()) {
            showSFImage = selection;
        }

//        Button inboxButton = new Button("Accueil", accueilImage);
//        inboxButton.setUIID("SideCommand");
//        inboxButton.getAllStyles().setPaddingBottom(0);
//        Container inbox = FlowLayout.encloseMiddle(inboxButton);
//        inbox.setLeadComponent(inboxButton);
//        inbox.setUIID("SideCommand");
//        inboxButton.addActionListener(e -> new AccueilForm().show());
//        getToolbar().addComponentToSideMenu(inbox);
        getToolbar().addCommandToSideMenu("Post Show", accueilImage, e -> new AccueilForm(res).show());
        getToolbar().addCommandToSideMenu("Accueil", propositionImage, e -> new ShowPublicationForm(res).show());
        getToolbar().addCommandToSideMenu("Evenement", evenementImage, e -> new EvenementForm(res).show());
        getToolbar().addCommandToSideMenu("Chat", chatImage, e -> new MessageForm(res).show());
        getToolbar().addCommandToSideMenu("Proposition", propImage, e -> new PropositionForm(res).show());
        getToolbar().addCommandToSideMenu("Show Forum", showSFImage, e -> new ShowForumForm(res).show());
        getToolbar().addCommandToSideMenu("Forum", showFImage, e -> new ForumForm(res).show());
        getToolbar().addCommandToSideMenu("Tache Show", showSTImage, e -> new ShowTacheForm(res).show());
        getToolbar().addCommandToSideMenu("Tache", showTImage, e -> new TacheForm(res).show());
        getToolbar().addCommandToSideMenu("Calendrier", calendarImage, e -> new CalendarForm(res).show());
        getToolbar().addCommandToSideMenu("Covoiturage", covoiturageImage, e -> new CovoiturageForm(res).show());
        getToolbar().addCommandToSideMenu("Profil", profilImage, e -> new ProfileForm(res).show());
        getToolbar().addCommandToSideMenu("Logout", null, e -> new SignInForm(res).show());

        // spacer
        getToolbar().addComponentToSideMenu(new Label(" ", "SideCommand"));
        EncodedImage encImg = EncodedImage.createFromImage(res.getImage("contact-a.png"), false);

        URLImage imgUrl = URLImage.createToStorage(encImg, Context.getInstance().getProfil().getImageName(), "http://localhost/MobilePHP/" + Context.getInstance().getProfil().getImageName());
        imgUrl.fetch();

        getToolbar().addComponentToSideMenu(new Label(imgUrl, "Container"));
        getToolbar().addComponentToSideMenu(new Label(Context.getInstance().getUser().getPrenom() + " " + Context.getInstance().getUser().getNom(), "SideCommandNoPad"));
        getToolbar().addComponentToSideMenu(new Label(Context.getInstance().getProfil().getLieu(), "SideCommandSmall"));
    }

    protected boolean isCurrentShwoST() {
        return false;
    }

    protected boolean isCurrentShwoF() {
        return false;
    }

    protected boolean isCurrentShwoT() {
        return false;
    }

    protected boolean isCurrentShwoSF() {
        return false;
    }

    protected boolean isCurrentAccueil() {
        return false;
    }

    protected boolean isCurrentShwoPub() {
        return false;
    }

    protected boolean isCurrentChatForm() {
        return false;
    }

    protected boolean isCurrentForum() {
        return false;
    }

    protected boolean isCurrentProposition() {
        return false;
    }

    protected boolean isCurrentEvenement() {
        return false;
    }

    protected boolean isCurrentCovoiturage() {
        return false;
    }

    protected boolean isCurrentGroupe() {
        return false;
    }

    protected boolean isCurrentProfil() {
        return false;
    }

    protected boolean isCurrentMessageForm() {
        return false;
    }
}

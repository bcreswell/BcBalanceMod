//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.*;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;

import static bcBalanceMod.BcBalanceMod.makeRelicOutlinePath;
import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class PersonalDigitalAssistant extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("PersonalDigitalAssistant");
    static final Texture IMG = TextureLoader.getTexture(makeRelicPath("personalDigitalAssistant.png"));
    static final Texture outline = TextureLoader.getTexture(makeRelicOutlinePath("personalDigitalAssistant.png"));
    
    public static final int BlockPerEvoke = 2;
    
    public PersonalDigitalAssistant()
    {
        super(ID, IMG, outline, RelicTier.RARE, LandingSound.HEAVY);
    }
    
    public String getUpdatedDescription()
    {
        return "Whenever you Evoke an orb, gain #b" + BlockPerEvoke + " Block. NL";
    }
    
    @Override
    public void onEvokeOrb(AbstractOrb orb)
    {
        if (BcUtility.isPlayerInCombat())
        {
            AbstractPlayer player = AbstractDungeon.player;
            addToBot(new GainBlockAction(player, player, BlockPerEvoke));
            flash();
        }
    }
}

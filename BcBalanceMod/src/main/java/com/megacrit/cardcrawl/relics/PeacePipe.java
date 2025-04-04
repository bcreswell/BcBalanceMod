package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.TokeOption;

import java.util.ArrayList;
import java.util.Iterator;

public class PeacePipe extends AbstractRelic
{
    public static final String ID = "Peace Pipe";
    
    public PeacePipe()
    {
        super("Peace Pipe", "peacePipe.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return "Remove a card or curse from your deck at Rest Sites to gain a random upgrade.";
    }
    
    public boolean canSpawn()
    {
        //peace pipe replaces the dedicated ritual slot, so it doesn't need to worry about being exclusive with the other campfire relics.
        return Settings.isEndless || (AbstractDungeon.floorNum < 48);
        
//        if (AbstractDungeon.floorNum >= 48 && !Settings.isEndless)
//        {
//            return false;
//        }
//        else
//        {
//            int campfireRelicCount = 0;
//
//            for (AbstractRelic r : AbstractDungeon.player.relics)
//            {
//                if ((r instanceof PeacePipe) || (r instanceof Shovel) || (r instanceof Girya))
//                {
//                    campfireRelicCount++;
//                }
//            }
//
//            return campfireRelicCount < 2;
//        }
    }
    
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options)
    {
        int tokeCount = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size();
        tokeCount += AbstractDungeon.player.masterDeck.getPurgeableCurses().size();
        
        options.add(new TokeOption(tokeCount > 0));
    }
    
    public AbstractRelic makeCopy()
    {
        return new PeacePipe();
    }
}

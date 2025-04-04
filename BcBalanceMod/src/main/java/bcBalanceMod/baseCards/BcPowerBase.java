package bcBalanceMod.baseCards;

import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;

public abstract class BcPowerBase extends AbstractPower
{
    public boolean upgraded;
    public AbstractPlayer player;
    public String imgPath;
    public BuffDebuffApplicationType applicationType;
    private static long uniquePowerIdSuffix = 0;
    
    //region ctors
    public BcPowerBase()
    {
        this(null, 1, false);
    }
    
    public BcPowerBase(boolean upgraded)
    {
        this(null, 1, upgraded);
    }
    
    public BcPowerBase(AbstractCreature owner)
    {
        this(owner, 1, false);
    }
    
    public BcPowerBase(AbstractCreature owner, int amount)
    {
        this(owner, amount, false);
    }
    
    public BcPowerBase(AbstractCreature owner, boolean upgraded)
    {
        this(owner, 1, upgraded);
    }
    
    public BcPowerBase(AbstractCreature owner, int amount, boolean upgraded)
    {
        player = AbstractDungeon.player;
        this.owner = owner;
        if (this.owner == null)
        {
            this.owner = player;
        }
        
        this.amount = amount;
        this.upgraded = upgraded;
        applicationType = getApplicationType();
        
        if (applicationType == BuffDebuffApplicationType.Unique)
        {
            this.amount = -1;
        }
        
        name = getDisplayName();
        ID = getId();
        if (applicationType == BuffDebuffApplicationType.SeparateApplications)
        {
            ID += uniquePowerIdSuffix;
            uniquePowerIdSuffix++;
        }
        
        type = getPowerType();
        canGoNegative = getCanGoNegative();
        
        if ((amount < 0) && canGoNegative)
        {
            type = PowerType.DEBUFF;
        }
        
        imgPath = getImagePath();
        if (imgPath == null)
        {
            loadRegion("confusion");
        }
        else if (imgPath.endsWith(".png"))
        {
            //removes the "32x32.png" or "35x35.png" from the end and replaces it with "84x84.png"
            String largePath = imgPath.substring(0, imgPath.length() - 9) + "84x84.png";
            
            Texture smallIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/" + imgPath);
            Texture largeIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/" + largePath);
            
            region48 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
            if (largeIcon != null)
            {
                region128 = new TextureAtlas.AtlasRegion(largeIcon, 0, 0, largeIcon.getWidth(), largeIcon.getHeight());
            }
            else
            {
                region128 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
            }
        }
        else
        {
            loadRegion(imgPath);
        }
        
        if (upgraded)
        {
            name += "+";
            ID += "+";
        }
        
        updateDescription();
    }
    //endregion
    
    public abstract String getDisplayName();
    
    public abstract String getId();
    
    public abstract String getImagePath();
    
    public abstract PowerType getPowerType();
    
    public abstract boolean getCanGoNegative();
    
    public String getFullDescription()
    {
        String baseDescription = getBaseDescription();
        return baseDescription;
    }
    
    public abstract String getBaseDescription();
    
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.Stacking;
    }
    
    public boolean isAppliedQuietly()
    {
        return false;
    }
    
    public final void updateDescription()
    {
        name = getDisplayName();
        description = getFullDescription();
    }
    
    public void stackPower(int stackAmount)
    {
        amount += stackAmount;
    
        if (applicationType == BuffDebuffApplicationType.Unique)
        {
            amount = -1;
        }
        else
        {
            if (amount < 0)
            {
                if (!canGoNegative)
                {
                    amount = 0;
                }
                else if (amount <= -999)
                {
                    amount = -999;
                }
            }
    
            if (amount >= 999)
            {
                amount = 999;
            }
        }
        
        if (stackAmount != 0)
        {
            fontScale = 8.0F;
            onPowerStacked();
        }
        updateDescription();
    }
    
    public final void reducePower(int reduceAmount)
    {
        stackPower(-reduceAmount);
        updateDescription();
    }
    
    public void onUpgraded()
    {
    }
    
    public void onPowerStacked()
    {
    
    }
    
    @Override
    public void atStartOfTurn()
    {
        super.atStartOfTurn();
        updateDescription();
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        super.atEndOfTurn(isPlayer);
        updateDescription();
    }
    
    public void onManualDiscard()
    {
    
    }
    
    public void onScry(int amount)
    {
    }
    
    public static enum BuffDebuffApplicationType
    {
        Stacking,
        StackingAllowNegative,
        Unique,
        SeparateApplications
    }
}

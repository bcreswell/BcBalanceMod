package com.megacrit.cardcrawl.monsters.beyond;

import bcBalanceMod.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class Maw extends AbstractMonster
{
    public static final String ID = "Maw";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP = 300;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = -40.0F;
    private static final float HB_W = 430.0F;
    private static final float HB_H = 360.0F;
    private static final int SLAM_DMG = 25;
    private static final int NOM_DMG = 5;
    private static final int A_2_SLAM_DMG = 30;
    private int slamDmg;
    private int nomDmg;
    private static final byte ROAR = 2;
    private static final byte SLAM = 3;
    private static final byte DROOL = 4;
    private static final byte NOMNOMNOM = 5;
    private boolean roared = false;
    private int turnCount = 1;
    private int strUp;
    private int terrifyDur;
    
    public Maw(float x, float y)
    {
        super(NAME, "Maw", 300, 0.0F, -40.0F, 430.0F, 360.0F, (String) null, x, y);
        loadAnimation("images/monsters/theForest/maw/skeleton.atlas", "images/monsters/theForest/maw/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        dialogX = -160.0F * Settings.scale;
        dialogY = 40.0F * Settings.scale;
        
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            setHp(350);
        }
        else
        {
            setHp(300);
        }
        
        strUp = 3;
        terrifyDur = 3;
        slamDmg = 25;
        nomDmg = 5;
    
        if (AbstractDungeon.ascensionLevel >= 2)
        {
            slamDmg = 30;
            nomDmg = 7;
        }
        
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            strUp = 5;
            terrifyDur = 5;
        }
        
        damage.add(new DamageInfo(this, slamDmg));
        damage.add(new DamageInfo(this, nomDmg));
    }
    
    int getNomCount()
    {
        return BcUtility.clamp(3, turnCount / 2, 100);
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case ROAR:
                addToBot(new SFXAction("MAW_DEATH", 0.1F));
                addToBot(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, terrifyDur, true), terrifyDur));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, terrifyDur, true), terrifyDur));
                roared = true;
                break;
            case SLAM:
                addToBot(new AnimateSlowAttackAction(this));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case DROOL:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strUp), strUp));
                break;
            case NOMNOMNOM:
                addToBot(new AnimateSlowAttackAction(this));
                int nomCount = getNomCount();
                for (int i = 0; i < nomCount; i++)
                {
                    addToBot(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.SKY.cpy())));
                    addToBot(new DamageAction(AbstractDungeon.player, damage.get(1), AbstractGameAction.AttackEffect.NONE));
                }
        }
        
        addToBot(new RollMoveAction(this));
    }
    
    protected void getMove(int num)
    {
        ++turnCount;
        if (!roared)
        {
            setMove(ROAR, AbstractMonster.Intent.STRONG_DEBUFF);
        }
        else if (num < 50 && !lastMove((byte) 5))
        {
            int nomCount = getNomCount();
            if (nomCount <= 1)
            {
                setMove(NOMNOMNOM, AbstractMonster.Intent.ATTACK, damage.get(1).base);
            }
            else
            {
                setMove(NOMNOMNOM, AbstractMonster.Intent.ATTACK, damage.get(1).base, nomCount, true);
            }
            
        }
        else if (!lastMove(SLAM) && !lastMove(NOMNOMNOM))
        {
            setMove(SLAM, AbstractMonster.Intent.ATTACK, damage.get(0).base);
        }
        else
        {
            setMove(DROOL, AbstractMonster.Intent.BUFF);
        }
    }
    
    public void die()
    {
        super.die();
        CardCrawlGame.sound.play("MAW_DEATH");
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Maw");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

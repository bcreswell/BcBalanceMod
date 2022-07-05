//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.WebEffect;

public class LouseDefensive extends AbstractMonster
{
    public static final String ID = "FuzzyLouseDefensive";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 11;
    private static final int HP_MAX = 17;
    private static final int A_2_HP_MIN = 12;
    private static final int A_2_HP_MAX = 18;
    private static final byte BITE = 3;
    private static final byte WEAKEN = 4;
    private boolean isOpen = true;
    private static final String CLOSED_STATE = "CLOSED";
    private static final String OPEN_STATE = "OPEN";
    private static final String REAR_IDLE = "REAR_IDLE";
    private final int biteDamage;
    private static final int WEAK_AMT = 2;
    
    public LouseDefensive(float x, float y)
    {
        super(NAME, "FuzzyLouseDefensive", 17, 0.0F, -5.0F, 180.0F, 140.0F, (String) null, x, y);
        loadAnimation("images/monsters/theBottom/louseGreen/skeleton.atlas", "images/monsters/theBottom/louseGreen/skeleton.json", 1.0F);
        TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            //setHp(12, 18);
            setHp(14, 20);
        }
        else
        {
            setHp(11, 17);
        }
        
        if (AbstractDungeon.ascensionLevel >= 2)
        {
            biteDamage = AbstractDungeon.monsterHpRng.random(6, 8);
        }
        else
        {
            biteDamage = AbstractDungeon.monsterHpRng.random(5, 7);
        }
        
        damage.add(new DamageInfo(this, biteDamage));
    }
    
    public void usePreBattleAction()
    {
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(9, 12))));
        }
        else if (AbstractDungeon.ascensionLevel >= 7)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(4, 8))));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(3, 7))));
        }
        
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case 3:
                if (!isOpen)
                {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                }
                
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AttackEffect.BLUNT_LIGHT));
                break;
            case 4:
                if (!isOpen)
                {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(1.2F));
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_FAST_3", MathUtils.random(0.88F, 0.92F), true));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new WebEffect(AbstractDungeon.player, hb.cX - 70.0F * Settings.scale, hb.cY + 10.0F * Settings.scale)));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR_IDLE"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.9F));
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_FAST_3", MathUtils.random(0.88F, 0.92F), true));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new WebEffect(AbstractDungeon.player, hb.cX - 70.0F * Settings.scale, hb.cY + 10.0F * Settings.scale)));
                }
                
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    public void changeState(String stateName)
    {
        if (stateName.equals("CLOSED"))
        {
            state.setAnimation(0, "transitiontoclosed", false);
            state.addAnimation(0, "idle closed", true, 0.0F);
            isOpen = false;
        }
        else if (stateName.equals("OPEN"))
        {
            state.setAnimation(0, "transitiontoopened", false);
            state.addAnimation(0, "idle", true, 0.0F);
            isOpen = true;
        }
        else if (stateName.equals("REAR_IDLE"))
        {
            state.setAnimation(0, "rear", false);
            state.addAnimation(0, "idle", true, 0.0F);
            isOpen = true;
        }
        else
        {
            state.setAnimation(0, "transitiontoopened", false);
            state.addAnimation(0, "rear", false, 0.0F);
            state.addAnimation(0, "idle", true, 0.0F);
            isOpen = true;
        }
        
    }
    
    protected void getMove(int num)
    {
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            if (num < 25)
            {
                if (lastMove((byte) 4))
                {
                    setMove((byte) 3, Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
                }
                else
                {
                    setMove(MOVES[0], (byte) 4, Intent.DEBUFF);
                }
            }
            else if (lastTwoMoves((byte) 3))
            {
                setMove(MOVES[0], (byte) 4, Intent.DEBUFF);
            }
            else
            {
                setMove((byte) 3, Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
            }
        }
        else if (num < 25)
        {
            if (lastTwoMoves((byte) 4))
            {
                setMove((byte) 3, Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
            }
            else
            {
                setMove(MOVES[0], (byte) 4, Intent.DEBUFF);
            }
        }
        else if (lastTwoMoves((byte) 3))
        {
            setMove(MOVES[0], (byte) 4, Intent.DEBUFF);
        }
        else
        {
            setMove((byte) 3, Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
        }
        
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FuzzyLouseDefensive");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Repulsor extends AbstractMonster
{
    public static final String ID = "Repulsor";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String ENCOUNTER_NAME_W = "Ancient Shapes Weak";
    public static final String ENCOUNTER_NAME = "Ancient Shapes";
    private static final float HB_X = -8.0F;
    private static final float HB_Y = -10.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final byte DAZE = 1;
    private static final byte ATTACK = 2;
    private int attackDmg;
    private int dazeAmt;
    
    public Repulsor(float x, float y)
    {
        super(NAME, "Repulsor", 35, -8.0F, -10.0F, 150.0F, 150.0F, (String) null, x, y + 10.0F);
        loadAnimation("images/monsters/theForest/repulser/skeleton.atlas", "images/monsters/theForest/repulser/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        dazeAmt = 2;
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            //setHp(31, 38);
            setHp(33, 40);
        }
        else
        {
            setHp(29, 35);
        }
        
        if (AbstractDungeon.ascensionLevel >= 2)
        {
            attackDmg = 13;
        }
        else
        {
            attackDmg = 11;
        }
        
        damage.add(new DamageInfo(this, attackDmg));
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), dazeAmt, true, true));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    protected void getMove(int num)
    {
        if (num < 40 && !lastMove(ATTACK))
        {
            setMove(ATTACK, AbstractMonster.Intent.ATTACK, damage.get(0).base);
        }
        else
        {
            setMove(DAZE, AbstractMonster.Intent.DEBUFF);
        }
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Repulsor");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

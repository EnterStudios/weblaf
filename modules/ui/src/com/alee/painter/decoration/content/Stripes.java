/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.painter.decoration.content;

import com.alee.api.data.BoxOrientation;
import com.alee.api.data.Orientation;
import com.alee.painter.decoration.DecorationException;
import com.alee.painter.decoration.IDecoration;
import com.alee.utils.CompareUtils;
import com.alee.utils.GraphicsUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Abstract content representing multiple parallel stripes.
 *
 * @param <E> component type
 * @param <D> decoration type
 * @param <I> content type
 * @author Mikle Garin
 */

@XStreamAlias ( "Stripes" )
public class Stripes<E extends JComponent, D extends IDecoration<E, D>, I extends Stripes<E, D, I>> extends AbstractContent<E, D, I>
{
    /**
     * Stripes orientation.
     */
    @XStreamAsAttribute
    protected Orientation orientation;

    /**
     * Stripes alignment.
     * It can contain different values depending on {@link #orientation}.
     */
    @XStreamAsAttribute
    protected BoxOrientation align;

    /**
     * Stripes data.
     * At least one {@link Stripe} must always be provided.
     */
    @XStreamImplicit
    protected List<Stripe> stripes;

    /**
     * Returns stripes orientation.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return stripes orientation
     */
    public Orientation getOrientation ( final E c, final D d )
    {
        if ( orientation != null )
        {
            return orientation;
        }
        else
        {
            throw new DecorationException ( "Stripe orientation must be specified" );
        }
    }

    /**
     * Returns stripes alignment within provided bounds.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return stripes alignment within provided bounds
     */
    public BoxOrientation getAlign ( final E c, final D d )
    {
        if ( align != null )
        {
            if ( getOrientation ( c, d ).isVertical () )
            {
                if ( CompareUtils.equals ( align, BoxOrientation.left, BoxOrientation.center, BoxOrientation.right ) )
                {
                    return align;
                }
                else
                {
                    throw new DecorationException ( "Vertical stripe cannot use " + align + " alignment" );
                }
            }
            else
            {
                if ( CompareUtils.equals ( align, BoxOrientation.top, BoxOrientation.center, BoxOrientation.bottom ) )
                {
                    return align;
                }
                else
                {
                    throw new DecorationException ( "Horizontal stripe cannot use " + align + " alignment" );
                }
            }
        }
        else
        {
            return BoxOrientation.center;
        }
    }

    /**
     * Returns stripes count.
     *
     * @return stripes count
     */
    protected int getStripesCount ()
    {
        return stripes != null ? stripes.size () : 0;
    }

    @Override
    public boolean isEmpty ( final E c, final D d )
    {
        return getStripesCount () == 0;
    }

    @Override
    protected void paintContent ( final Graphics2D g2d, final E c, final D d, final Rectangle bounds )
    {
        // Display settings
        final boolean ltr = isLeftToRight ( c, d );
        final Orientation orientation = getOrientation ( c, d );
        final BoxOrientation align = getAlign ( c, d );

        // Painting each stripe
        for ( int i = 0; i < stripes.size (); i++ )
        {
            // Current stripe
            final Stripe stripe = stripes.get ( i );

            // Calculating stripe coordinates
            final int x1;
            final int y1;
            final int x2;
            final int y2;
            if ( orientation.isVertical () )
            {
                x1 = x2 = ltr ? bounds.x + i : bounds.x + bounds.width - i - 1;
                y1 = bounds.y;
                y2 = bounds.y + bounds.height - 1;
            }
            else
            {
                x1 = bounds.x;
                x2 = bounds.x + bounds.width - 1;
                y1 = y2 = bounds.y + i;
            }

            // Painting stripe
            final Stroke stroke = GraphicsUtils.setupStroke ( g2d, stripe.getStroke (), stripe.getStroke () != null );
            final Paint op = GraphicsUtils.setupPaint ( g2d, stripe.getPaint ( x1, y1, x2, y2 ) );
            g2d.drawLine ( x1, y1, x2, y2 );
            GraphicsUtils.restorePaint ( g2d, op );
            GraphicsUtils.restoreStroke ( g2d, stroke, stripe.getStroke () != null );
        }
    }

    @Override
    protected Dimension getContentPreferredSize ( final E c, final D d, final Dimension available )
    {
        final int stripes = getStripesCount ();
        final Orientation orientation = getOrientation ( c, d );
        return new Dimension ( orientation.isVertical () ? stripes : 0, orientation.isVertical () ? 0 : stripes );
    }

    @Override
    public I merge ( final I content )
    {
        super.merge ( content );
        orientation = content.isOverwrite () || content.orientation != null ? content.orientation : orientation;
        align = content.isOverwrite () || content.align != null ? content.align : align;
        stripes = content.isOverwrite () || content.stripes != null ? content.stripes : stripes;
        return ( I ) this;
    }
}
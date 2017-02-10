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

import com.alee.painter.decoration.IDecoration;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract icon {@link IContent} implementation.
 * Override {@link #getIcon(JComponent, IDecoration)} method to return displayed icon.
 *
 * @param <E> component type
 * @param <D> decoration type
 * @param <I> content type
 * @author Mikle Garin
 */

public abstract class AbstractIconContent<E extends JComponent, D extends IDecoration<E, D>, I extends AbstractIconContent<E, D, I>>
        extends AbstractContent<E, D, I>
{
    @Override
    public String getId ()
    {
        return id != null ? id : "icon";
    }

    @Override
    public boolean isEmpty ( final E c, final D d )
    {
        return getIcon ( c, d ) == null;
    }

    @Override
    protected void paintContent ( final Graphics2D g2d, final E c, final D d, final Rectangle bounds )
    {
        final Icon icon = getIcon ( c, d );
        if ( icon != null )
        {
            final int x = bounds.x + bounds.width / 2 - icon.getIconWidth () / 2;
            final int y = bounds.y + bounds.height / 2 - icon.getIconHeight () / 2;
            icon.paintIcon ( c, g2d, x, y );
        }
    }

    @Override
    protected Dimension getContentPreferredSize ( final E c, final D d, final Dimension available )
    {
        final Icon icon = getIcon ( c, d );
        return icon != null ? new Dimension ( icon.getIconWidth (), icon.getIconHeight () ) : new Dimension ( 0, 0 );
    }

    /**
     * Returns icon to paint.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return icon to paint
     */
    protected abstract Icon getIcon ( E c, D d );
}
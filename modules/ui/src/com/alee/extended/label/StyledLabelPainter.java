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

package com.alee.extended.label;

import com.alee.laf.label.AbstractLabelPainter;
import com.alee.painter.decoration.IDecoration;

/**
 * Basic painter for {@link WebStyledLabel} component.
 * It is used as {@link WStyledLabelUI} default painter.
 *
 * @param <E> component type
 * @param <U> component UI type
 * @param <D> decoration type
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-WebStyledLabel">How to use WebStyledLabel</a>
 */

public class StyledLabelPainter<E extends WebStyledLabel, U extends WStyledLabelUI, D extends IDecoration<E, D>>
        extends AbstractLabelPainter<E, U, D> implements IStyledLabelPainter<E, U>
{
    /**
     * Implementation is used completely from {@link AbstractLabelPainter}.
     */
}
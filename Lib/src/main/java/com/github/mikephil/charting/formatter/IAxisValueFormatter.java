package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.AxisBase;


@Deprecated
public interface IAxisValueFormatter
{

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     *
     * @deprecated Extend {@link ValueFormatter} and use {@link ValueFormatter#getAxisLabel(float, AxisBase)}
     */
    @Deprecated
    String getFormattedValue(float value, AxisBase axis);
}
